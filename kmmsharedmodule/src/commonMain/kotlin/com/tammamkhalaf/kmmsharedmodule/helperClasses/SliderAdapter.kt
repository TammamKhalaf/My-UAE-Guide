package com.tammamkhalaf.kmmsharedmodule.helperClasses

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.tammamkhalaf.myuaeguide.R
import com.tammamkhalaf.myuaeguide.R.string

class SliderAdapter(var context: Context) : PagerAdapter() {
    var layoutInflater: LayoutInflater? = null
    var images = intArrayOf(
            R.drawable.search_place,
            R.drawable.make_a_call,
            R.drawable.add_missing_place,
            R.drawable.sit_back_and_relax
    )

    var resources:Resources = context.resources

    private val headings: Array<String> by lazy { resources.getStringArray(R.array.titles)}


    private var descriptions = intArrayOf(
            string.first_slide_desc,
            string.second_slide_desc,
            string.third_slide_desc,
            string.first_slide_desc
    )

    override fun getCount(): Int {
        return headings.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as ConstraintLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater!!.inflate(R.layout.slides_layout, container, false)
        val imageView = view.findViewById<ImageView>(R.id.slider_image)
        val heading = view.findViewById<TextView>(R.id.slider_heading)
        val desc = view.findViewById<TextView>(R.id.slider_desc)
        imageView.setImageResource(images[position])


        heading.setText(headings[position])
        desc.setText(descriptions[position])
        Log.d(TAG, "instantiateItem: headings "+position+heading.setText(headings[position]))
        Log.d(TAG, "instantiateItem: descriptions "+position+desc.setText(descriptions[position]))
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }

    companion object {
        private const val TAG = "SliderAdapter"
    }
}