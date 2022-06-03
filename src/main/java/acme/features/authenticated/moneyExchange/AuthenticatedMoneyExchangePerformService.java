package acme.features.authenticated.moneyExchange;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.currencyExchangeFunction.ExchangeMoneyFunctionService;
import acme.entities.initialConfiguration.InitialConfiguration;
import acme.forms.MoneyExchange;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.datatypes.Money;
import acme.framework.roles.Authenticated;
import acme.framework.services.AbstractPerformService;

@Service
public class AuthenticatedMoneyExchangePerformService implements AbstractPerformService<Authenticated, MoneyExchange> {

	@Autowired
	protected AuthenticatedMoneyExchangeRepository repository;
	
	@Autowired
	protected ExchangeMoneyFunctionService exchangeMoneyFunctionService;

	@Override
	public boolean authorise(final Request<MoneyExchange> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<MoneyExchange> request, final MoneyExchange entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "source", "targetCurrency", "date", "target");
	}

	@Override
	public void unbind(final Request<MoneyExchange> request, final MoneyExchange entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		final InitialConfiguration initialConfig;
		
		initialConfig = this.repository.findInitialConfiguration();

		request.unbind(entity, model, "source", "targetCurrency", "date", "target");
		model.setAttribute("acceptedCurrencies", initialConfig.getAcceptedCurrencies());
		model.setAttribute("defaultCurrency", initialConfig.getDefaultCurrency());
	}

	@Override
	public MoneyExchange instantiate(final Request<MoneyExchange> request) {
		assert request != null;

		MoneyExchange result;

		result = new MoneyExchange();

		return result;
	}

	@Override
	public void validate(final Request<MoneyExchange> request, final MoneyExchange entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void perform(final Request<MoneyExchange> request, final MoneyExchange entity, final Errors errors) {
		assert request != null;
		assert entity != null;

		Money source, target;
		String targetCurrency;
		Date date;
		MoneyExchange exchange;

		source = request.getModel().getAttribute("source", Money.class);
		targetCurrency = request.getModel().getAttribute("targetCurrency", String.class);
		exchange = this.exchangeMoneyFunctionService.computeMoneyExchange(source, targetCurrency);
		errors.state(request, exchange != null, "*", "authenticated.money-exchange.form.label.api-error");
		if (exchange == null) {
			entity.setTarget(null);
			entity.setDate(null);
		} else {
			target = exchange.getTarget();
			entity.setTarget(target);
			date = exchange.getDate();
			entity.setDate(date);
		}
	}

	// Ancillary methods ------------------------------------------------------
}
