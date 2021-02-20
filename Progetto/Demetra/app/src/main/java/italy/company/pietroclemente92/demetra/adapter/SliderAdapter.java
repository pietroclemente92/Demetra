package italy.company.pietroclemente92.demetra.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;
import italy.company.pietroclemente92.demetra.R;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    int images[] = {
            R.drawable.icon_shop_vector,
            R.drawable.slide_product_list,
            R.drawable.slide_faq
    };

    int headings[] = {
            R.string.first_slide_title_shopping_area,
            R.string.second_slide_title_shopping_area,
            R.string.third_slide_title_shopping_area
    };

    int description[] = {
            R.string.first_slide_desc_shopping_area,
            R.string.second_slide_desc_shopping_area,
            R.string.third_slide_desc_shopping_area
    };

    public SliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout_on_boarding_shopping_area, container,false);

        //Hooks
        ImageView imageView = view.findViewById(R.id.slider_image);
        TextView headingView = view.findViewById(R.id.slider_heading);
        TextView descView = view.findViewById(R.id.slider_desc);

        imageView.setImageResource(images[position]);
        headingView.setText(headings[position]);
        descView.setText(description[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
