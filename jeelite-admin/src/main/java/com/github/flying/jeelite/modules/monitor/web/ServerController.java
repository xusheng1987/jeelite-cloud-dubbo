package com.github.flying.jeelite.modules.monitor.web;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.flying.jeelite.common.web.BaseController;
import com.github.flying.jeelite.modules.monitor.entity.SysInfo;
import com.github.flying.jeelite.modules.monitor.api.SysInfoService;

/**
 * 服务器监控
 * 
 */
@Controller
@RequestMapping("${adminPath}/monitor/server")
public class ServerController extends BaseController {

	@DubboReference
	private SysInfoService sysInfoService;

	@RequiresPermissions("monitor:server:view")
	@RequestMapping(value = "")
	public String sysInfo(Model model) {
		SysInfo sysInfo = sysInfoService.getSysInfo();
		model.addAttribute("sysInfo", sysInfo);
		return "modules/monitor/serverInfo";
	}
}