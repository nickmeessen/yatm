package nl.enterprisecoding.android.sufficient.handlers;

import android.app.Dialog;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import nl.enterprisecoding.android.sufficient.activities.MainActivity;

/**
 * ClickHandler for the Colour button
 *
 * @author Sjors Roelofs
 */
public class ColourButtonClickHandler implements IButtonClickHandler {

    private MainActivity mActivity;
    private int mInputColour;
    private GradientDrawable mBgShape;
    private Dialog mColourDialog;
    private int mRandColour;

    public void setData(MainActivity activity, int inputColour, GradientDrawable bgShape, Dialog colourDialog, int randColour) {
        setActivity(activity);

        mInputColour = inputColour;
        mBgShape = bgShape;
        mColourDialog = colourDialog;
        mRandColour = randColour;
    }

    @Override
    public void setActivity(MainActivity activity) {
        mActivity = activity;
    }

    @Override
    public void onClick(View v) {
        int chosenColour;

        if (mInputColour != 0) {
            chosenColour = mActivity.getResources().getColor(mInputColour);
            mBgShape.setColor(mActivity.getResources().getColor(mInputColour));
        } else {
            chosenColour = mRandColour;
            mBgShape.setColor(mRandColour);
        }

        mActivity.setFinalColour(chosenColour);
        mColourDialog.dismiss();
    }

}
