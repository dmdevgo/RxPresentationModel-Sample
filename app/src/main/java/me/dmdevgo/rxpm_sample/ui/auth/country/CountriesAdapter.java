package me.dmdevgo.rxpm_sample.ui.auth.country;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import me.dmdevgo.rxpm_sample.R;
import me.dmdevgo.rxpm_sample.model.Country;

/**
 * @author Dmitriy Gorbunov
 */
public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.ViewHolder> {

    private final Context context;
    private List<Country> countries;
    private ItemClickListener<Country> itemClickListener;

    public interface ItemClickListener<T> {
        void onItemClick(T item);
    }

    public CountriesAdapter(Context context, List<Country> countries) {
        this.context = context;
        this.countries = countries;
    }

    public void setItemClickListener(ItemClickListener<Country> listener) {
        itemClickListener = listener;
    }

    public void setData(List<Country> countries) {
        this.countries = countries;
        notifyDataSetChanged();
    }

    private Country getItem(int position) {
        return countries.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_country, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemCount() {
        if (countries == null) {
            return 0;
        } else {
            return countries.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView countryName;
        TextView countryCode;
        Country country;

        public ViewHolder(View itemView) {
            super(itemView);
            countryName = (TextView) itemView.findViewById(R.id.country_name);
            countryCode = (TextView) itemView.findViewById(R.id.country_code);
            itemView.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(country);
                }
            });
        }

        public void bind(Country country) {
            this.country = country;
            countryName.setText(country.getDisplayName());
            countryCode.setText(String.format(Locale.ENGLISH, "+%d", country.getCountryCallingCode()));
        }
    }
}
