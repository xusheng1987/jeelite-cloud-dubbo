/**
 * Copyright &copy; 2017-2018 <a href="https://github.com/xusheng1987/jeelite">jeelite</a> All rights reserved.
 */
package com.github.flying.jeelite.modules.test.web;

import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.flying.jeelite.common.rest.Result;
import com.github.flying.jeelite.common.utils.StringUtils;
import com.github.flying.jeelite.common.web.BaseController;
import com.github.flying.jeelite.modules.test.entity.TestTree;
import com.github.flying.jeelite.modules.sys.utils.UserUtils;
import com.github.flying.jeelite.modules.test.api.TestTreeService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 树结构生成Controller
 *
 * @author flying
 */
@Controller
@RequestMapping(value = "${adminPath}/test/testTree")
public class TestTreeController extends BaseController {

	@DubboReference
	private TestTreeService testTreeService;

	@ModelAttribute
	public TestTree get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return testTreeService.get(id);
		} else {
			return new TestTree();
		}
	}

	@RequiresPermissions("test:testTree:view")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/test/testTreeList";
	}

	@ResponseBody
	@RequiresPermissions("test:testTree:view")
	@RequestMapping(value = "data")
	public List<TestTree> data() {
		List<TestTree> list = testTreeService.findAll();
		return list;
	}

	@RequiresPermissions("test:testTree:view")
	@RequestMapping(value = "form")
	public String form(TestTree testTree, Model model) {
		if (testTree.getParent() != null && StringUtils.isNotBlank(testTree.getParent().getId())) {
			testTree.setParent(testTreeService.get(testTree.getParent().getId()));
		}
		model.addAttribute("testTree", testTree);
		return "modules/test/testTreeForm";
	}

	@ResponseBody
	@RequiresPermissions("test:testTree:edit")
	@RequestMapping(value = "save")
	public Result save(TestTree testTree) {
		beanValidator(testTree);
		
		UserUtils.preSave(testTree);
		testTreeService.save(testTree);
		return renderSuccess("保存树结构成功");
	}

	@ResponseBody
	@RequiresPermissions("test:testTree:edit")
	@RequestMapping(value = "delete")
	public Result delete(TestTree testTree) {
		testTreeService.delete(testTree);
		return renderSuccess("删除树结构成功");
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<TestTree> list = testTreeService.findList(new TestTree());
		for (int i = 0; i < list.size(); i++) {
			TestTree e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId != null && !extId.equals(e.getId())
					&& e.getParentIds().indexOf("," + extId + ",") == -1)) {
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}

}