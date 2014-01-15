package nl.enterprisecoding.android.sufficient.handlers;

import android.app.Dialog;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import nl.enterprisecoding.android.sufficient.activities.CategoryActivity;
import nl.enterprisecoding.android.sufficient.activities.MainActivity;

/**
 * ClickHandler for the Colour button
 *
 * @author Sjors Roelofs
 */
public class ColourButtonClickHandler implements View.OnClickListener {

    private int mInputColour;
    private GradientDrawable mBgShape;
    private Dialog mColourDialog;
    private int mRandColour;
    private int mCategoryColour;

    public ColourButtonClickHandler() {

    }

    /**
     * Sets data for the onClick
     *
     * @param bgShape
     * @param colourDialog
     * @param inputColour
     * @param randColour
     */
    public void setData(GradientDrawable bgShape, Dialog colourDialog, int inputColour, int randColour, int finalColour) {
        mInputColour = inputColour;
        mBgShape = bgShape;
        mColourDialog = colourDialog;
        mRandColour = randColour;
        mCategoryColour = finalColour;
    }

    /**
     * onClick handler
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        if (mInputColour != 0) {
            mBgShape.setColor(v.getContext().getResources().getColor(mInputColour));
            mCategoryColour = v.getContext().getResources().getColor(mInputColour);
        } else {
            mBgShape.setColor(mRandColour);
            mCategoryColour = mRandColour;
        }

        mColourDialog.dismiss();
    }
}
