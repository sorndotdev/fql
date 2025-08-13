package dev.sorn.fql.api;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import static dev.sorn.fql.api.Checks.checkNotNull;

public class Money extends Amount {
    protected final Locale locale;

    private Money(BigDecimal amount, Currency currency) {
        this(amount, currency, Locale.US);
    }

    private Money(BigDecimal value, Currency currency, Locale locale) {
        super(value, currency);
        this.locale = checkNotNull("locale", locale);
    }

    public static Money money(BigDecimal amount, Currency currency) {
        return new Money(amount, currency);
    }

    public Money withLocale(Locale locale) {
        return new Money(value, (Currency) unit, locale);
    }

    @Override
    public Money add(DataPointValue obj) {
        return (Money) super.add(obj);
    }

    @Override
    public Money sub(DataPointValue obj) {
        return (Money) super.sub(obj);
    }

    @Override
    public Money mul(DataPointValue obj) {
        return (Money) super.mul(obj);
    }

    @Override
    public Money div(DataPointValue obj) {
        return (Money) super.div(obj);
    }

    @Override
    public Money neg() {
        return (Money) super.neg();
    }

    @Override
    public Money abs() {
        return (Money) super.abs();
    }

    @Override
    public Money zero() {
        return (Money) super.zero();
    }

    @Override
    protected Amount create(BigDecimal value, Unit unit) {
        return new Money(value, (Currency) unit, this.locale);
    }

    @Override
    public String toString() {
        try {
            java.util.Currency javaCurrency = java.util.Currency.getInstance(((Currency) unit).code());
            return formatCurrency(javaCurrency);
        } catch (IllegalArgumentException e) {
            return super.toString();
        }
    }

    private String formatCurrency(java.util.Currency currency) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        formatter.setCurrency(currency);
        formatter.setMinimumFractionDigits(unit.minorUnit());
        formatter.setMaximumFractionDigits(unit.minorUnit());
        if (value.signum() < 0) {
            String positive = formatter.format(value.abs());
            return "-" + positive.replace("(", "").replace(")", "");
        }
        return formatter.format(value);
    }
}