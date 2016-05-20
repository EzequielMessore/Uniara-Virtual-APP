package br.com.uniaravirtual.exception;

import br.com.uniaravirtual.R;
import br.com.uniaravirtual.util.AppUtil;
import br.com.uniaravirtual.util.StringsUtils;

public class NotAvailableNetwork extends Exception {

    public NotAvailableNetwork() {
        super(StringsUtils.getStringFromResource(AppUtil.CONTEXT_APPLICATION, R.string.msg_error_connection));
    }

}
