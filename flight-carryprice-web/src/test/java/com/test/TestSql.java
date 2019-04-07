package com.test;


import com.flight.carryprice.common.util.HttpClientUtils;

import java.util.HashMap;
import java.util.Map;

public class TestSql {

	public static void main(String[] args) throws Exception {
//		String sql11 = "update ccs_flight_m set refund_change_price='1425' where flight_id=718908";
//		String sql1111 = "insert into ccs_scheduled_task (order_id,task_type,param1,scan_status,scan_count,create_time) values('OR18101723160745811296',108,'10',0,0,now())";
//		String sqlabcd = "update ccs_scheduled_task set scan_status=0,scan_count=0 where id=6621790";
//		String sqlaa = "update cbbbs_order_retticket set retticket_status=0 where id=25872";
//		String sqlabc = "update ccs_billsys_retticket  set apply_refund_price=1338  where id=33954";
//		String sql11111 = "update ccs_order_retticket set retticket_type=1 where id=26673";
//		String sql111111 = "delete from ccs_order_retticket where id=26673";
////		String sql = "update ccs_scheduled_task set scan_status=0,scan_count=0 where id=8402838";
//		String sql11121212 = "update ccs_refund_notify set scan_status=0,scan_count=0 where id=36868";
//		String sql111 = "update ccs_order_m set request_ip='10.187.125.85' where request_ip is null and salesys_time>'2018-11-01 00:00:00'";
//		String sqlabcddd = "update ccs_distribution_info set expect_distribution_time='2018-07-10 12:00:00' where id=219913";
//		String sql1 = "update ccs_passenger_m set ticketno='8762109167070' where passenger_id='2438892'";
//		String sqla = "update ccs_order_m set order_status=6 where id=2375621";
//		String sqlab = "update ccs_order_retticket set salesys_retticket_sum='1088.00',billsys_retticket_sum='1088.00',retticket_status=0,platform_refund_status=0 where id=128693";
//		String sql123213 = "INSERT INTO al_popedom_tblmodule (AppCode,MenuCode,ModuleCode,ModuleName,ModuleNameStyle,ModuleURL,NewModuleURL,Target,ModuleSort,ParentId,IsOpen,IsEnabled) \n" +
//				" VALUES ( 33,3,56,'FD运价数据差异','比较不同',(NULL), 'baseData/fdData/dbAndRedisDifferenceList','mainFrame', 39, 0  , 0 , 0);";
//
			String slqPolicy = "INSERT INTO `al_popedom_tblrolepopedom` VALUES ('4', '1', '2', '4', null);\n" +
					"INSERT INTO `al_popedom_tblrolepopedom` VALUES ('5', '1', '2', '5', null);\n" +
					"INSERT INTO `al_popedom_tblrolepopedom` VALUES ('6', '1', '2', '9', null);\n" +
					"INSERT INTO `al_popedom_tblrolepopedom` VALUES ('7', '1', '3', '6', null);\n" +
					"INSERT INTO `al_popedom_tblrolepopedom` VALUES ('8', '1', '3', '8', null);\n" +
					"INSERT INTO `al_popedom_tblrolepopedom` VALUES ('9', '1', '3', '10', null);\n" +
					"INSERT INTO `al_popedom_tblrolepopedom` VALUES ('10', '1', '3', '12', null);\n" +
					"INSERT INTO `al_popedom_tblrolepopedom` VALUES ('11', '1', '3', '13', null);\n" +
					"INSERT INTO `al_popedom_tblrolepopedom` VALUES ('12', '1', '4', '7', null);\n" +
					"INSERT INTO `al_popedom_tblrolepopedom` VALUES ('13', '1', '4', '11', null);\n" +
					"INSERT INTO `al_popedom_tblrolepopedom` VALUES ('14', '1', '4', '14', null);";

			String url = "http://cp.man.jd.com/sqltest/exceSql";
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("name", "sql");
			paramMap.put("pwd", "7db0757c-7f67-4ffd-9a1e-94f29ef0869c");
			paramMap.put("sql", slqPolicy);
			String resp = HttpClientUtils.sendPostRequest(url, paramMap, "UTF-8");
			System.out.println(resp);

		// String url =
		// "http://localhost:8081/sqltest/exceRefundChangeRuleImport";
		// Map<String, String> paramMap = new HashMap<>();
		// paramMap.put("name", "sql");
		// paramMap.put("pwd", "7db0757c-7f67-4ffd-9a1e-94f29ef0869c");
		// String resp = HttpClientUtils.sendPostRequest(url, paramMap,
		// "UTF-8");
		// System.out.println(resp);
//		System.out.println("35".equals(35));

	}

}
