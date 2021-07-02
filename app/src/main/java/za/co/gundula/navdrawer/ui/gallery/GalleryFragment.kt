package za.co.gundula.navdrawer.ui.gallery

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import za.co.gundula.navdrawer.NavDrawerApplication
import za.co.gundula.navdrawer.R
import za.co.gundula.navdrawer.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private var isConnected = false
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val galleryViewModel: GalleryViewModel by viewModels {
            GalleryViewModelFactory((activity?.application as NavDrawerApplication).repository)
        }
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textGallery
        galleryViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })

        val recyclerView: RecyclerView = binding.recyclerViewCats

        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        isConnected = activeNetwork?.isConnectedOrConnecting == true

        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                recyclerView.layoutManager = GridLayoutManager(context, 4)
            }
            else -> {
                recyclerView.layoutManager = GridLayoutManager(context, 2)
            }
        }

        galleryViewModel.init(isConnected)
        galleryViewModel.allCats.observe(viewLifecycleOwner) { cats ->
            if (!isConnected && cats.isEmpty()) {
                networkSnackBar(recyclerView)
            } else {
                val adapter = CatsListRecyclerViewAdapter(cats)
                adapter.getClickedCatSubject().subscribe { cat_id ->
                    Toast.makeText(context,"Cat ID : $cat_id", Toast.LENGTH_LONG).show()
                }
                recyclerView.adapter = adapter
            }
        }
        return root
    }

    private fun networkSnackBar(view: View) {
        Snackbar.make(view, getString(R.string.network_toast), Snackbar.LENGTH_INDEFINITE).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}