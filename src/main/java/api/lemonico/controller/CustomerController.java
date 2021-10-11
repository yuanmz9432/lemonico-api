package api.lemonico.controller;



import api.lemonico.annotation.LcConditionParam;
import api.lemonico.annotation.LcPaginationParam;
import api.lemonico.annotation.LcSortParam;
import api.lemonico.attribute.LcPagination;
import api.lemonico.attribute.LcResultSet;
import api.lemonico.attribute.LcSort;
import api.lemonico.repository.CustomerRepository;
import api.lemonico.resource.CustomerResource;
import api.lemonico.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerController
{

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final static String COLLECTION_RESOURCE_URI = "/customers";

    private final static String CUSTOMER_RESOURCE_URI = COLLECTION_RESOURCE_URI + "/{id}";

    private final CustomerService service;

    @RequestMapping(method = RequestMethod.GET, path = COLLECTION_RESOURCE_URI)
    public ResponseEntity<LcResultSet<CustomerResource>> getCustomers(
        @LcConditionParam CustomerRepository.Condition condition,
        @LcPaginationParam LcPagination pagination,
        @LcSortParam(allowedValues = {}) LcSort lcSort) {
        if (condition == null) {
            condition = CustomerRepository.Condition.DEFAULT;
        }
        var sort = CustomerRepository.Sort.fromLcSort(lcSort);
        return ResponseEntity.ok(service.getResourceList(condition, pagination, sort));
    }

    @RequestMapping(method = RequestMethod.GET, path = CUSTOMER_RESOURCE_URI)
    public String getCustomer(@PathVariable Long id) {
        logger.info("id: {}", id);
        return "getCustomer";
    }

    @RequestMapping(method = RequestMethod.PUT, path = COLLECTION_RESOURCE_URI)
    public String updateCustomer(@RequestBody CustomerResource resource) {
        logger.info("resource: {}", resource);
        return "updateCustomer";
    }

    @RequestMapping(method = RequestMethod.POST, path = COLLECTION_RESOURCE_URI)
    public String createCustomer(@RequestBody CustomerResource resource) {
        logger.info("resource: {}", resource);
        return "createCustomer";
    }

    @RequestMapping(method = RequestMethod.DELETE, path = CUSTOMER_RESOURCE_URI)
    public String deleteCustomer(@PathVariable Long id) {
        logger.info("id: {}", id);
        return "deleteCustomer";
    }
}
