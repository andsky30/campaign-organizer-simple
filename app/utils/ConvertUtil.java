package utils;

import model.CampaignStatus;
import service.exceptions.InvalidStatusException;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class ConvertUtil {

    public static String convertBigDecimalToString(BigDecimal bd) {
        bd = bd.setScale(2, BigDecimal.ROUND_DOWN);
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(0);
        df.setGroupingUsed(false);
        return df.format(bd).replace(',', '.');
    }

    public static CampaignStatus resolveCampaignStatus(String status) {
        if (status.equals("on")) {
            return CampaignStatus.ON;
        } else if (status.equals("off")) {
            return CampaignStatus.OFF;
        } else throw new InvalidStatusException();
    }

    public static String convertCampaignStatusToString(CampaignStatus status) {
        if (status.equals(CampaignStatus.ON)) {
            return "on";
        } else if (status.equals(CampaignStatus.OFF)) {
            return "off";
        } else throw new InvalidStatusException();
    }
}
