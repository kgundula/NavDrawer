package za.co.gundula.navdrawer.ui.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import za.co.gundula.navdrawer.model.Cat
import za.co.gundula.navdrawer.GlideApp
import za.co.gundula.navdrawer.R

internal class CatsListRecyclerViewAdapter(private val catsLIst: List<Cat>) :
    RecyclerView.Adapter<CatsListRecyclerViewAdapter.CatsViewHolder>() {

    private var onClickSubject = PublishSubject.create<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cats, parent, false)
        return CatsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatsViewHolder, position: Int) {
        val cat = catsLIst[position]
        GlideApp.with(holder.catImage)
            .load(cat.imageUrl)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(holder.catImage)
        holder.catImage.setOnClickListener { onClickSubject.onNext(cat.id) }
    }

    override fun getItemCount(): Int {
        return catsLIst.size
    }

    fun getClickedCatSubject(): Observable<String> {
        return onClickSubject.hide()
    }

    internal inner class CatsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal val catImage = view.findViewById<AppCompatImageView>(R.id.cat_image)
    }

}