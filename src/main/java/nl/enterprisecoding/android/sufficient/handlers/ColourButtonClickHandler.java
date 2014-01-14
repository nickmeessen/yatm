package nl.enterprisecoding.android.sufficient.handlers;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

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

    /**
     * Set data to use in the onClick
     *
     * @param inputColour
     * @param bgShape
     * @param colourDialog
     * @param randColour
     */
    public ColourButtonClickHandler(GradientDrawable bgShape, Dialog colourDialog, int inputColour, int randColour) {
        mInputColour = inputColour;
        mBgShape = bgShape;
        mColourDialog = colourDialog;
        mRandColour = randColour;
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
        } else {
            mBgShape.setColor(mRandColour);
        }

        mColourDialog.dismiss();
    }

    /**
     * Retrieves the activity
     *
     * @return The requested activity
     */
    public Activity getActivity() {
        return mActivity;
    }

    /**
     * Retrieves the input colour
     *
     * @return The requested input colour of type int
     */
    public int getInputColour() {
        return mInputColour;
    }

    /**
     * Retrieves the bgShape
     *
     * @return The requested bgShape of type GradientDrawable
     */
    public GradientDrawable getBgShape() {
        return mBgShape;
    }

    /**
     * Retrieves the colour dialog
     *
     * @return The requested colour dialog of type Dialog
     */
    public Dialog getColourDialog() {
        return mColourDialog;
    }

    /**
     * Retrieves the random colour
     *
     * @return The requested random colour of type int
     */
    public int getRandColour() {
        return mRandColour;
    }

}
