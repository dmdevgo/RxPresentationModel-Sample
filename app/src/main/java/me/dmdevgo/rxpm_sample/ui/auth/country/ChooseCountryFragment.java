package me.dmdevgo.rxpm_sample.ui.auth.country;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;

import butterknife.BindView;
import me.dmdevgo.rxpm_sample.R;
import me.dmdevgo.rxpm_sample.ui.base.BaseFragment;

/**
 * @author Dmitriy Gorbunov
 */

public class ChooseCountryFragment extends BaseFragment<ChooseCountryPm> {

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @Nullable @BindView(R.id.action_search) SearchView searchView;

    private CountriesAdapter adapter;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_choose_country;
    }

    @Override
    public ChooseCountryPm providePresentationModel(Bundle bundle) {
        return new ChooseCountryPm();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new CountriesAdapter(getContext(), null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onInitToolbar(Toolbar toolbar) {
        super.onInitToolbar(toolbar);
        toolbar.inflateMenu(R.menu.search);
        searchView = (SearchView) toolbar.getMenu().findItem(R.id.action_search).getActionView();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onBindPresentationModel(ChooseCountryPm pm) {
        super.onBindPresentationModel(pm);
        subscribe(pm.countries(), countries -> adapter.setData(countries));
        subscribe(RxSearchView.queryTextChanges(searchView).map(CharSequence::toString), pm.searchQuery());
        adapter.setItemClickListener(country -> pm.countryClick());
    }
}
