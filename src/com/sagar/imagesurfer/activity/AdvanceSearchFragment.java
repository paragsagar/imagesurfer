package com.sagar.imagesurfer.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.sagar.imagesurfer.R;
import com.sagar.imagesurfer.model.SearchQuery;

public class AdvanceSearchFragment extends Fragment {

	private SearchQuery search;

	private EditText etSite;
	private Spinner spinImgSize, spincolor, spinImgType;
	private Button btnSave;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_advance_search,container, false);
		if (search == null)
			search = new SearchQuery();

		//Setup view
		setupViewWidgets(view);

		//Setup Spinner listeners
		setupSpinnerListeners();

		//initialize the values
		setupWidgetValues();

		btnSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				getSearch().site = etSite.getText().toString();
				AdvanceSearchFragmentListener listner = (AdvanceSearchFragmentListener) getActivity();
				listner.onFinishAdvanceSearch(getSearch());
			}
		});

		return view;
	}

	/**
	 * 
	 */
	private void setupWidgetValues() {
		setSpinnerToValue(spincolor, getSearch().color);
		setSpinnerToValue(spinImgSize, getSearch().size);
		setSpinnerToValue(spinImgType, getSearch().type);
		etSite.setText(getSearch().site);

	}

	/**
	 * @param view
	 */
	private void setupViewWidgets(View view) {
		spinImgSize = (Spinner) view.findViewById(R.id.spnImageSize);
		spincolor = (Spinner) view.findViewById(R.id.spnColorFilter);
		spinImgType = (Spinner) view.findViewById(R.id.spnImageType);
		etSite = (EditText) view.findViewById(R.id.etSiteFilter);
		btnSave = (Button) view.findViewById(R.id.btnSave);
	}

	private void setupSpinnerListeners() {
		setUpHandlerForColor();
		setUpHandlerForType();
		setUpHandlerForSize();
	}

	private void setUpHandlerForColor() {
		spincolor.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int arg2, long arg3) {
				getSearch().color = ((TextView) view).getText().toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				;
			}
		});
	}

	private void setUpHandlerForType() {
		spinImgType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int arg2, long arg3) {
				getSearch().type = ((TextView) view).getText().toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				;
			}
		});
	}

	private void setUpHandlerForSize() {
		spinImgSize.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int arg2, long arg3) {
				getSearch().size = ((TextView) view).getText().toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				;
			}
		});
	}

	public SearchQuery getSearch() {
		return search;
	}

	public void setSearch(SearchQuery search) {
		this.search = search;
	}

	public void setSpinnerToValue(Spinner spinner, String value) {
		int index = 0;
		SpinnerAdapter adapter = spinner.getAdapter();
		for (int i = 0; i < adapter.getCount(); i++) {
			if (adapter.getItem(i).equals(value)) {
				index = i;
				break; // terminate loop
			}
		}
		spinner.setSelection(index);
	}

	
	//Interface to return back to Activity
	public interface AdvanceSearchFragmentListener {
		public void onFinishAdvanceSearch(SearchQuery search);
	}

}
