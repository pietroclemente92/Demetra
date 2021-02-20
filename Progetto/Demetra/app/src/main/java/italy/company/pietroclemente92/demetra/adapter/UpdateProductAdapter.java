package italy.company.pietroclemente92.demetra.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import italy.company.pietroclemente92.demetra.R;

public class UpdateProductAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> productList;
    ArrayList<Integer> iconList;
    LayoutInflater inflter;

    public UpdateProductAdapter(Context applicationContext, ArrayList<String> productList, ArrayList<Integer> iconList) {
        this.context = context;
        this.productList = productList;
        this.iconList = iconList;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.listview_private_update_product, null);
        TextView country = (TextView) view.findViewById(R.id.nameProduct);
        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        country.setText(productList.get(i));
        icon.setImageResource(iconList.get(i));

        return view;
    }
}