package com.example.easyrules.coupon;

import com.example.easyrules.coupon.model.Order;
import com.example.easyrules.coupon.model.Project;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRuleFactory;
import org.jeasy.rules.support.reader.YamlRuleDefinitionReader;

import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pengpdx@163.com
 * @version 1.0
 * @description TODO
 * @date 2023/9/27 4:29 PM
 */
public class Launcher {

    public static void main(String[] args) throws Exception {
        //create a person instance (fact)
        List<Project> projects = new ArrayList<>();
        projects.add(new Project("测试项目", "AZ001", BigDecimal.TEN));

        Order order = new Order();
        order.setId("0001");
        order.setProjects(projects);
        order.setAmount(BigDecimal.TEN);

        Facts facts = new Facts();
        facts.put("order", order);

        // create rules
        MVELRuleFactory ruleFactory = new MVELRuleFactory(new YamlRuleDefinitionReader());
        String fileName = args.length != 0 ? args[0] : "/Users/pengdx/Workspance/coupon-engine/easy-rules/src/main/resources/static/free-rule.yml";
        Rule alcoholRule = ruleFactory.createRule(new FileReader(fileName));

        // create a rule set
        Rules rules = new Rules();
        rules.register(alcoholRule);

        //create a default rules engine and fire rules on known facts
        RulesEngine rulesEngine = new DefaultRulesEngine();

        System.out.println("Tom: Hi! can I have some Vodka please?");
        rulesEngine.fire(rules, facts);
    }
}
