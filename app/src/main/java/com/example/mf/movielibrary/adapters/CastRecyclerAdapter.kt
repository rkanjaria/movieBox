package com.example.mf.movielibrary.adapters

import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.models.castmodel.Cast
import files.inflate
import files.loadImage
import files.photoUrl
import kotlinx.android.synthetic.main.cast_recycler_layout.view.*
import kotlinx.android.synthetic.main.horizontal_footer_layout.view.*
import org.jetbrains.anko.displayMetrics

class CastRecyclerAdapter(val castList: List<Cast>, val castAdapterListener: OnCastAdapterListener, val isHorizontal : Boolean = false) :
        RecyclerView.Adapter<CastRecyclerAdapter.MyViewHolder>() {

    private val NORMAL_VIEW = 1
    private val FOOTER_VIEW = 2

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {

        if (holder is CastViewHolder) {
            holder.bindViews(castList[position])
        } else {
            (holder as FooterViewHolder).bindFooter()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        when (viewType) {
            FOOTER_VIEW -> return FooterViewHolder(parent.inflate(R.layout.horizontal_footer_layout, false))
            else -> return CastViewHolder(parent.inflate(R.layout.cast_recycler_layout, false))
        }
    }

    override fun getItemViewType(position: Int): Int {

        when (position) {
            castList.size -> return FOOTER_VIEW
            8 -> return FOOTER_VIEW
            else -> return NORMAL_VIEW
        }
    }

    override fun getItemCount(): Int {
        if (castList.size < 8) {
            return castList.size + 1
        } else {
            return 9
        }
    }

    inner open class CastViewHolder(castView: View) : MyViewHolder(castView), View.OnClickListener {
        override fun onClick(view: View?) {
            castAdapterListener.onCastClicked(castList.get(adapterPosition))
        }

        val view = castView
        fun bindViews(castModel: Cast) {

            if(isHorizontal) {
                val widthInDp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100f, view.context.displayMetrics)
                view.layoutParams.width = widthInDp.toInt()
            }
            view.actorsPic.loadImage(photoUrl + castModel.profilePath, R.color.darkGrey, false)
            view.actorsName.text = castModel.name
            view.actorsCharacterName.text = castModel.character
            view.setOnClickListener(this)
        }
    }

    inner class FooterViewHolder(footerView: View) : MyViewHolder(footerView) {
        val fView = footerView
        fun bindFooter() {
            fView.moreButton.setOnClickListener {
                Toast.makeText(it.context, "more Clicked", Toast.LENGTH_SHORT).show()
            }
        }
    }

    inner open class MyViewHolder(mainView: View) : RecyclerView.ViewHolder(mainView)

    interface OnCastAdapterListener {
        fun onCastClicked(castModel: Cast)
    }
}