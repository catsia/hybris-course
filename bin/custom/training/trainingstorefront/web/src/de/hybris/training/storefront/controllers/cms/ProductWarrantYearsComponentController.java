/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package de.hybris.training.storefront.controllers.cms;

import com.training.model.ProductWarrantyYearsComponentModel;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.training.storefront.controllers.ControllerConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@Controller("ProductWarrantyYearsController")
@RequestMapping(value = ControllerConstants.Actions.Cms.ProductWarrantyYearsComponent)
public class ProductWarrantYearsComponentController extends AbstractAcceleratorCMSComponentController<ProductWarrantyYearsComponentModel> {

    @Resource(name = "productVariantFacade")
    private ProductFacade productFacade;

    @Override
    protected void fillModel(final HttpServletRequest request, final Model model, final ProductWarrantyYearsComponentModel component) {
        final ProductModel currentProduct = getRequestContextData(request).getProduct();
        if (currentProduct != null) {
            final ProductData productData = productFacade.getProductForCodeAndOptions(currentProduct.getCode(), null);
            model.addAttribute("warrantyYears", currentProduct.getWarrantyYears());
        }
    }
}
