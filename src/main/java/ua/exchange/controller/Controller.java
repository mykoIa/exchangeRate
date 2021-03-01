package ua.exchange.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ua.exchange.Description;
import ua.exchange.db.DataBaseService;
import ua.exchange.model.CurrenciesRateListWithAverages;
import ua.exchange.model.ExchangeModel;
import ua.exchange.util.ParseCurrencyUtil;

import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/exchangeRate")
public class Controller {
    
    public static final Logger LOG = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private DataBaseService dbService;

    @GetMapping("/latest")
    @ResponseBody
    @ApiOperation(value = "Request for a list of courses for all sources, with values average market rates")
    public CurrenciesRateListWithAverages getExchangeRate(@ApiParam(Description.BASE_CURRENCY) @RequestParam("base") String baseCurrency,
                                                          @ApiParam(Description.CURRENCY) @RequestParam("currencies") String currencies) {
        Optional<Currency> currency = ParseCurrencyUtil.parseCurrency(baseCurrency);
        if (!currency.isPresent()) {
            return new CurrenciesRateListWithAverages(Collections.emptyList());
        }
        List<ExchangeModel> exchangeRateList = dbService.findLastEntry(
                currency.get().getCurrencyCode(), ParseCurrencyUtil.parseCurrencies(currencies));
        LOG.warn(exchangeRateList);
        return new CurrenciesRateListWithAverages(exchangeRateList);
    }

    @GetMapping("/timerange")
    @ResponseBody
    @ApiOperation(value = "Request for issuing a list of average exchange rates for all sources for a period")
    public List<ExchangeModel> getExchangeRateByDate(@ApiParam(Description.BASE_CURRENCY) @RequestParam("base") String baseCurrency,
                                                     @ApiParam(Description.CURRENCY) @RequestParam("currencies") String currencies,
                                                     @ApiParam(Description.DATE_FROM) @RequestParam("dateFrom") Long dateFrom,
                                                     @ApiParam(Description.DATE_TO) @RequestParam("dateTo") Long dateTo) {
        Optional<Currency> currency = ParseCurrencyUtil.parseCurrency(baseCurrency);
        if (!currency.isPresent()) {
            return Collections.emptyList();
        }
        return dbService.findByTimeRange(currency.get().getCurrencyCode(), ParseCurrencyUtil.parseCurrencies(currencies), dateFrom, dateTo);
    }

}
