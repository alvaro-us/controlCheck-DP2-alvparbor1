package acme.currencyExchangeFunction;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import acme.components.ExchangeRate;
import acme.entities.currencyExchanges.CurrencyExchange;
import acme.forms.MoneyExchange;
import acme.framework.datatypes.Money;
import acme.framework.helpers.StringHelper;

@Service
public class ExchangeMoneyFunctionService  {
	
	@Autowired
	protected CurrencyExchangeFunctionRepository currencyRepository;

	public MoneyExchange computeMoneyExchange(final Money source, final String targetCurrency) {
		assert source != null;
		assert !StringHelper.isBlank(targetCurrency);

		MoneyExchange result;
		RestTemplate api;
		ExchangeRate record;
		String sourceCurrency;
		Double sourceAmount, targetAmount, rate;
		Money target;
		
		CurrencyExchange currencyExchange;
		final Calendar calendar = Calendar.getInstance();
		Date deadlineDate;
		
		sourceCurrency = source.getCurrency();
		sourceAmount = source.getAmount();
		
		currencyExchange = this.currencyRepository.findOneCurrencyExchangeBySourceAndTarget(sourceCurrency, targetCurrency);
			
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		deadlineDate =calendar.getTime();
		
		if(currencyExchange.getTransactionMoment().before(deadlineDate)) {
			//usamos la api si se supera la fecha límite desde la última transacción de ese tipo o si esa transacción
			//no existe como entrada en la base de datos
			try {
				api = new RestTemplate();

				record = api.getForObject( //
					"https://api.exchangerate.host/latest?base={0}&symbols={1}", //
					ExchangeRate.class, //
					sourceCurrency, //
					targetCurrency //
				);

				assert record != null;
				rate = record.getRates().get(targetCurrency);
				targetAmount = rate * sourceAmount;

				target = new Money();
				target.setAmount(targetAmount);
				target.setCurrency(targetCurrency);

				result = new MoneyExchange();
				result.setSource(source);
				result.setTargetCurrency(targetCurrency);
				result.setDate(record.getDate());
				result.setTarget(target);
				
				currencyExchange.setSource(sourceCurrency);
				currencyExchange.setTarget(targetCurrency);
				currencyExchange.setRate(record.getRates().get(targetCurrency));
				currencyExchange.setTransactionMoment(record.getDate());
				
				this.currencyRepository.save(currencyExchange);
				
			} catch (final Throwable oops) {
				result = null;
			}
			
		} else { //no llamamos a la api sino que usamos la consulta del repo
			rate = currencyExchange.getRate();
			targetAmount = rate * sourceAmount;
			
			target = new Money();
			target.setAmount(targetAmount);
			target.setCurrency(targetCurrency);

			result = new MoneyExchange();
			result.setSource(source);
			result.setTargetCurrency(targetCurrency);
			result.setDate(currencyExchange.getTransactionMoment());
			result.setTarget(target);
		}

		return result;
	}
}
