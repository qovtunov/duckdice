package WebTests;

import Components.PageTemplate;
import org.junit.Test;

public class PageTestTemplate {

    public PageTemplate pageTemplate;

    @Test
    public void testExample() {
        pageTemplate = new PageTemplate();
        pageTemplate.someAction();
        pageTemplate.someElement.click();
    }


}
