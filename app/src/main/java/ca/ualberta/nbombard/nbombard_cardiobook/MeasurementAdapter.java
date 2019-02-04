package ca.ualberta.nbombard.nbombard_cardiobook;

import android.content.Context;
import android.content.Intent;
import android.icu.util.Measure;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * My custom RecyclerViewAdapter.
 * This helps keep track of displaying all of the measurements.
 * The measurement information is kept in a cardview, so that it would be easier to display.
 *
 * @author Nicholas Bombardieri
 * written on 2019/02/04
 * I referenced https://www.youtube.com/watch?v=ZXoGG2XTjzU&t=640s
 *      "RecyclerView OnClickListener to New Activity" by CodingWithMitch.
 *       Uploaded on 2018/01/02. Accessed on 2019/02/04.
 */

public class MeasurementAdapter extends
        RecyclerView.Adapter<MeasurementAdapter.MeasurementViewHolder>{

    private Context context;
    private List<Measurement> measurementList;

    /**
     * Constructor function for my adapter.
     * @param context, the context of the activity that this will be displayed in.
     * @param measurementList, the list of measurments.
     */

    public MeasurementAdapter(Context context, List<Measurement> measurementList) {
        this.context = context;
        this.measurementList = measurementList;
    }


    /**
     * Inflates my list_layout for a card.
     * @param viewGroup
     * @param i
     * @return returns the layout for the card.
     */
    @NonNull
    @Override
    public MeasurementViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_layout, null);
        MeasurementViewHolder holder = new MeasurementViewHolder(view);
        return holder;
    }


    /**
     * Grabs data from measurement and puts it into it's respective card.
     * @param measurementViewHolder, the card being worked on.
     * @param i, the index of the current measurement in the measurement list.
     */
    @Override
    public void onBindViewHolder(@NonNull final MeasurementViewHolder measurementViewHolder, final int i) {
        Measurement measurement = measurementList.get(i);
        measurementViewHolder.textViewHRValue.setText(String.valueOf(measurement.getHeartRate()));
        measurementViewHolder.textViewDiaPressValue.setText(String.valueOf(measurement.getDiaPress()));
        measurementViewHolder.textViewSysPressValue.setText(String.valueOf(measurement.getSysPress()));
        measurementViewHolder.textViewDateValue.setText(measurement.getDate());
        measurementViewHolder.id = i;

        if (measurement.getAbnormality()) {
            measurementViewHolder.imageView.setImageDrawable(context.getResources().
                    getDrawable(R.drawable.sad));
        } else {
            measurementViewHolder.imageView.setImageDrawable(context.getResources().
                    getDrawable(R.drawable.happy));
        }

        measurementViewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, viewMeasurement.class);
                intent.putExtra("measurementId", i);
                context.startActivity(intent);
            }
        });

    }


    /**
     * returns the size of the list for drawing purposes
     * @return, the size of measurementList.
     */
    @Override
    public int getItemCount() {
        return measurementList.size();
    }


    /**
     * the class for the RecyclerViewHolder. This is the section with the cards which hold the
     *          information.
     */
    class MeasurementViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textViewDateValue;
        TextView textViewDiaPressValue;
        TextView textViewSysPressValue;
        TextView textViewHRValue;
        ConstraintLayout parentLayout;
        int id;

        /**
         * constructor for the cards. Grabs the references to the appropriate textView fields.
         * @param itemView, the card information.
         */

        public MeasurementViewHolder(@NonNull View itemView) {
            super(itemView);


            imageView = itemView.findViewById(R.id.imageView);
            textViewDateValue = itemView.findViewById(R.id.textViewDateValue);
            textViewDiaPressValue = itemView.findViewById(R.id.textViewDiaPressValue);
            textViewSysPressValue = itemView.findViewById(R.id.textViewSysPressValue);
            textViewHRValue = itemView.findViewById(R.id.textViewHRValue);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }

}
