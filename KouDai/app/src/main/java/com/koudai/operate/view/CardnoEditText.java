package com.koudai.operate.view;


import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

public class CardnoEditText extends EditText {
    public CardnoEditText(Context paramContext) {
        super(paramContext);
        init();
    }

    public CardnoEditText(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init();
    }

    public CardnoEditText(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        init();
    }

    private int getSpaceSize(CharSequence paramCharSequence) {
        int i = 0;
        if (TextUtils.isEmpty(paramCharSequence))
            i = 0;
        while (true) {
            i = 0;
            for (int j = 0; j < paramCharSequence.length(); j++)
                if (paramCharSequence.charAt(j) == ' ')
                    return i;
            i++;
        }

    }

    private void init() {
        setLongClickable(false);
        addTextChangedListener(new MyTextWatcher());
        super.setInputType(2);
    }

    private String removeSpace(CharSequence paramCharSequence) {
        if (TextUtils.isEmpty(paramCharSequence))
            return "";
        return paramCharSequence.toString().trim().replaceAll(" ", "");
    }

    public String getCardno() {
        return removeSpace(getText().toString().trim());
    }

    public void setInputType(int paramInt) {
    }

    private class MyTextWatcher
            implements TextWatcher {
        int beforeTextLength = 0;
        StringBuffer buffer = new StringBuffer();
        boolean deleteBetween;
        private int deleteIndex;
        boolean isChanged = false;
        int konggeNumberB = 0;
        int location = 0;
        int onTextLength = 0;
        char[] tempChar;

        private MyTextWatcher() {
        }

        public void afterTextChanged(Editable paramEditable) {
            String str = "";
            if (this.isChanged) {
                this.location = CardnoEditText.this.getSelectionEnd();
                if (this.deleteBetween) {
                    this.buffer.delete(this.deleteIndex, 1 + this.deleteIndex);
                    CardnoEditText.this.setText(this.buffer.toString());
                    Selection.setSelection(CardnoEditText.this.getText(), -1 + this.location);
                    this.isChanged = false;
                    return;
                }
                int i = 0;
                while (i < this.buffer.length())
                    if (this.buffer.charAt(i) == ' ')
                        this.buffer.deleteCharAt(i);
                    else
                        i++;
                int j = 0;
                int k = 0;
                while (j < this.buffer.length()) {
                    if ((j == 4) || (j == 9) || (j == 14) || (j == 19)) {
                        this.buffer.insert(j, ' ');
                        k++;
                    }
                    j++;
                }
                if (k > this.konggeNumberB)
                    this.location += k - this.konggeNumberB;
                this.tempChar = new char[this.buffer.length()];
                this.buffer.getChars(0, this.buffer.length(), this.tempChar, 0);
                str = this.buffer.toString();
                if (this.location <= str.length())
                    this.location = str.length();
            }
            while (true) {
                CardnoEditText.this.setText(str);
                Selection.setSelection(CardnoEditText.this.getText(), this.location);
                this.isChanged = false;
                if (paramEditable.length() <= 23)
                    break;
                paramEditable.delete(23, paramEditable.length());
                Selection.setSelection(CardnoEditText.this.getText(), 23);
                if (this.location < 0)
                    this.location = 0;
                return;
            }
        }

        public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {
            this.beforeTextLength = paramCharSequence.length();
            if (this.buffer.length() > 0)
                this.buffer.delete(0, this.buffer.length());
            this.konggeNumberB = 0;
            for (int i = 0; i < paramCharSequence.length(); i++)
                if (paramCharSequence.charAt(i) == ' ')
                    this.konggeNumberB = (1 + this.konggeNumberB);
        }

        public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {
            this.deleteBetween = false;
            if ((paramInt3 > 0) && (paramInt1 < -1 + paramCharSequence.length()) && (CardnoEditText.this.removeSpace(paramCharSequence).length() > 19)) {
                this.deleteIndex = paramInt1;
                this.deleteBetween = true;
            }
            this.onTextLength = paramCharSequence.length();
            this.buffer.append(paramCharSequence.toString());
            if ((this.onTextLength == this.beforeTextLength) || (this.onTextLength <= 3) || (this.isChanged)) {
                this.isChanged = false;
                return;
            }
            this.isChanged = true;
        }
    }
}
