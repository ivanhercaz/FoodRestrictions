/*
 * Copyright (C) 2015-2016 The Food Restriction Project Team
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 */

package br.com.mochiladepano.foodrestriction;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class FoodMessages extends Fragment implements AdapterView.OnItemSelectedListener {

    private FoodIconList foodIconList;

    public FoodMessages() {
        foodIconList = new FoodIconList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_restriction_message,
                container, false);

        List<FoodIconItem> restrictList = foodIconList.getFoodRestrictionList(true);


        LinearLayout llAllergic = (LinearLayout) v.findViewById(R.id.llAllergic);
        LinearLayout llDontEat = (LinearLayout) v.findViewById(R.id.llDontEat);

        Spinner spinner = (Spinner) v.findViewById(R.id.spinner_language);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(v.getContext(),
                R.array.languages_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        int nAllergic = 0;
        int nDontEat = 0;

        for (final FoodIconItem iconItem : restrictList) {
            final String foodName = getResources().getString(iconItem.getNameId());

            TextView tv = new TextView(v.getContext());
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            tv.setTypeface(null, Typeface.BOLD_ITALIC);
            tv.setText("* " + foodName);

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (iconItem.getRestrictionType()) {
                        case FoodIconList.FOOD_RESTRICTION_TYPE_ALLERGIC:
                            Snackbar.make(v, v.getResources().getString(R.string.message_allergic_to) + " " + foodName, Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            break;
                        case FoodIconList.FOOD_RESTRICTION_TYPE_DONT_EAT:
                            Snackbar.make(v, v.getResources().getString(R.string.message_dont_eat) + " " + foodName, Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            break;
                    }
                }
            });


            if (iconItem.getRestrictionType() == FoodIconList.FOOD_RESTRICTION_TYPE_ALLERGIC) {
                llAllergic.addView(tv);
                ++nAllergic;
            } else {
                llDontEat.addView(tv);
                ++nDontEat;
            }

        }

        if (nAllergic == 0) {
            TextView allergicTitle = (TextView) v.findViewById(R.id.messages_allergic_title);
            TextView allergicText = (TextView) v.findViewById(R.id.messages_allergic_text);

            allergicTitle.setVisibility(View.GONE);
            allergicText.setVisibility(View.GONE);

            TextView tv = new TextView(v.getContext());
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            tv.setTypeface(null, Typeface.BOLD_ITALIC);
            tv.setText("* " + getResources().getString(R.string.food_msg_not_allergic));
            llAllergic.addView(tv);
        }

        if (nDontEat == 0) {
            TextView dontEatTitle = (TextView) v.findViewById(R.id.messages_dont_eat_title);
            TextView dontEatText = (TextView) v.findViewById(R.id.messages_dont_eat_text);

            dontEatTitle.setVisibility(View.GONE);
            dontEatText.setVisibility(View.GONE);

            TextView tv = new TextView(v.getContext());
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            tv.setTypeface(null, Typeface.BOLD_ITALIC);
            tv.setText("* " + getResources().getString(R.string.food_msg_not_picker));
            llDontEat.addView(tv);
        }
        return v;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Locale locale = new Locale("pt-BR");
        Locale.setDefault(locale);
        getResources().getConfiguration().locale = locale;
        view.getContext().getResources().updateConfiguration( getResources().getConfiguration(),
                view.getContext().getResources().getDisplayMetrics());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}