package com.example.accessingdatamysql.list;

import com.example.accessingdatamysql.demo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller    // This means that this class is a Controller
@RequestMapping(path="/list")
public class ListController {

    @Autowired
    private ListService nodeService;

    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody Node addNode (@RequestParam String value
            , @RequestParam Integer nextNodeId) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

       return  nodeService.addNode(value,nextNodeId);

    }

    /**
     * Return all nodes starting with give node id. If node id is null retrun all nodes.
     *
     * @param nextNodeId
     * @return
     */
    @GetMapping(path="/all")
    public @ResponseBody Iterable<Node> getAllNodes(@RequestParam(required = false)  String nextNodeId) {
        // This returns a JSON or XML with the users

        return nodeService.getAllNodeValuesStartingWith(nextNodeId);
    }
}
