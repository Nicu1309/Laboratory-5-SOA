package soa.web;

import java.util.*;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class SearchController {

	@Autowired
	  private ProducerTemplate producerTemplate;

	@RequestMapping("/")
    public String index() {
        return "index";
    }


    @RequestMapping(value="/search")
    @ResponseBody
    public Object search(@RequestParam("q") String q) {
        //Divide q param to split the keyword and max count
        // s has count
        // q has keywords
        int i = q.indexOf("max");
        String s = q.substring(i);
        String[] opt = s.split(":");
        s = opt[1];
        Integer count = Integer.parseInt(s);
        q = q.substring(0,i);
        //Create the headers
        Map<String,Object> headers = new HashMap<String,Object>();
        headers.put("CamelTwitterKeywords",q);
        headers.put("CamelTwitterCount",count);
        return producerTemplate.requestBodyAndHeaders("direct:search", "", headers);
    }
}