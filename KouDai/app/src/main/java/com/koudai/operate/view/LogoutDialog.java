package com.koudai.operate.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.koudai.operate.R;
import com.koudai.operate.activity.LoginActivity;
import com.koudai.operate.base.BaseActivity;
import com.koudai.operate.fragment.InviteFragment;
import com.koudai.operate.utils.AccountUtil;

import org.apache.commons.logging.Log;


/**
 * Created by Administrator on 2016/8/24.
 */
public class LogoutDialog extends Dialog implements DialogInterface.OnCancelListener {
    public LogoutDialog(final Context context, int themeResId, final LogoutDialogListener listener) {
        super(context, themeResId);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_logout, null);
        this.setContentView(view);
        view.findViewById(R.id.bt_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                    ((Activity) context).overridePendingTransition(R.anim.push_right_in, R.anim.not_exit_push_left_out);
                    cancel();
                    listener.onClick();
                }
            }
        });
        setCanceledOnTouchOutside(false);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().width = (int) (displayMetrics.widthPixels * 0.9);
        setOnCancelListener(this);
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {
        AccountUtil.setIsLogoutDialogShowing(false);
    }

    public interface LogoutDialogListener {
        void onClick();
    }
}
