package com.training.controllers.cms;


import com.training.model.QuestionsCMSComponentModel;
import de.hybris.platform.addonsupport.controllers.cms.AbstractCMSAddOnComponentController;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.product.ProductModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;
import com.training.controllers.QuestionsControllerConstants;

@Controller("QuestionsCMSComponentController")
@RequestMapping(value = QuestionsControllerConstants.Actions.Cms.QuestionsCMSComponent)
public class QuestionsCMSComponentController extends AbstractCMSAddOnComponentController<QuestionsCMSComponentModel> {

    @Resource(name = "productVariantFacade")
    private ProductFacade productFacade;


    @Override
    protected void fillModel(HttpServletRequest request, Model model, QuestionsCMSComponentModel component) {
        final ProductModel currentProduct = getRequestContextData(request).getProduct();
        if (currentProduct != null) {
            final ProductData productData = productFacade.getProductForCodeAndOptions(currentProduct.getCode(), null);

            model.addAttribute("questions", currentProduct.getQuestion().stream().limit(component.getNumberOfQuestionsToShow()).collect(Collectors.toList()));
            model.addAttribute("size", component.getSize());
        }
    }

    private ProductFacade getProductFacade() {
        return productFacade;
    }
}
