package com.korov.alicloud.sentinel.service;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DemoService {
    public static void main(String[] args) {
        initFlowRules();
        while (true) {
            // 1.5.0 版本开始可以直接利用 try-with-resources 特性，自动 执行entry.exit()
            try (Entry entry = SphU.entry("HelloWorld")) {
                /**
                 * try中的代码被SphU.entry("HelloWorld")和entry.exit()包围
                 * 既可以被保护
                 */
                System.out.println("hello world");
            } catch (BlockException ex) {
                // 处理被流控的逻辑
                System.out.println("blocked!");
            }
        }
    }

    /**
     * 基于注解实现资源保护
     * 此方法就成了我们的一个资源
     * 注意注解支持模块需要配合 Spring AOP 或者 AspectJ 一起使用
     */
    @SentinelResource("HelloWorld")
    public void helloWorld() {
        System.out.println("hello world");
    }

    public void init() {
        initFlowRules();
        while (true) {
            // 1.5.0 版本开始可以直接利用 try-with-resources 特性，自动 执行entry.exit()
            try (Entry entry = SphU.entry("HelloWorld")) {
                /**
                 * try中的代码被SphU.entry("HelloWorld")和entry.exit()包围
                 * 既可以被保护
                 */
                System.out.println("hello world");
            } catch (BlockException ex) {
                // 处理被流控的逻辑
                System.out.println("blocked!");
            }
        }
    }

    /**
     * 定义资源的保护规则
     */
    private static void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("HelloWorld");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to 20. 每秒只能通过20次请求
        rule.setCount(20);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }
}
