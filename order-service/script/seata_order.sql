/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost:3306
 Source Schema         : seata_order

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 29/05/2019 10:30:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for branch_transaction
-- ----------------------------
DROP TABLE IF EXISTS `branch_transaction`;
CREATE TABLE `branch_transaction` (
  `sysno` bigint(20) NOT NULL AUTO_INCREMENT,
  `xid` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL,
  `branch_id` bigint(20) NOT NULL,
  `args_json` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL COMMENT '1，初始化；2，已提交；3，已回滚',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`sysno`) USING BTREE,
  UNIQUE KEY `xid` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='事务记录表';

-- ----------------------------
-- Records of branch_transaction
-- ----------------------------
BEGIN;
INSERT INTO `branch_transaction` VALUES (36, '192.168.1.4:8091:2012781558', 2012781560, '[{\"appid\":\"dk-order\",\"buyerUserSysNo\":1,\"paymentType\":1,\"receiveAddress\":\"朝阳区长安街001号\",\"receiveContact\":\"斯密达\",\"receiveContactPhone\":\"18728828296\",\"receiveDivisionSysNo\":110105,\"receiveZip\":\"000001\",\"sellerCompanyCode\":\"SC001\",\"soAmt\":430,\"soItems\":[{\"costPrice\":200,\"dealPrice\":215,\"originalPrice\":232,\"productName\":\"刺力王\",\"productSysNo\":1,\"quantity\":2,\"soSysNo\":5877786866424549376}],\"stockSysNo\":1,\"sysNo\":5877786866424549376}]', 2, '2019-05-28 14:26:33', '2019-05-28 14:26:34');
COMMIT;

-- ----------------------------
-- Table structure for so_item
-- ----------------------------
DROP TABLE IF EXISTS `so_item`;
CREATE TABLE `so_item` (
  `sysno` bigint(20) NOT NULL AUTO_INCREMENT,
  `so_sysno` bigint(20) DEFAULT NULL,
  `product_sysno` bigint(20) DEFAULT NULL,
  `product_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商品名称',
  `cost_price` decimal(16,6) DEFAULT NULL COMMENT '成本价',
  `original_price` decimal(16,6) DEFAULT NULL COMMENT '原价',
  `deal_price` decimal(16,6) DEFAULT NULL COMMENT '成交价',
  `quantity` int(11) DEFAULT NULL COMMENT '数量',
  PRIMARY KEY (`sysno`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单明细表';

-- ----------------------------
-- Records of so_item
-- ----------------------------
BEGIN;
INSERT INTO `so_item` VALUES (15, 5877786866424549376, 1, '刺力王', 200.000000, 232.000000, 215.000000, 2);
COMMIT;

-- ----------------------------
-- Table structure for so_master
-- ----------------------------
DROP TABLE IF EXISTS `so_master`;
CREATE TABLE `so_master` (
  `sysno` bigint(20) NOT NULL,
  `so_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `buyer_user_sysno` bigint(20) DEFAULT NULL COMMENT '下单用户号',
  `seller_company_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '卖家公司编号',
  `receive_division_sysno` bigint(20) NOT NULL,
  `receive_address` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `receive_zip` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `receive_contact` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `receive_contact_phone` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `stock_sysno` bigint(20) DEFAULT NULL,
  `payment_type` tinyint(4) DEFAULT NULL COMMENT '支付方式：1，支付宝，2，微信',
  `so_amt` decimal(16,6) DEFAULT NULL COMMENT '订单总额',
  `status` tinyint(4) DEFAULT NULL COMMENT '10,创建成功，待支付；30；支付成功，待发货；50；发货成功，待收货；70，确认收货，已完成；90，下单失败；100已作废',
  `order_date` timestamp NULL DEFAULT NULL COMMENT '下单时间',
  `paymemt_date` timestamp NULL DEFAULT NULL COMMENT '支付时间',
  `delivery_date` timestamp NULL DEFAULT NULL COMMENT '发货时间',
  `receive_date` timestamp NULL DEFAULT NULL COMMENT '发货时间',
  `appid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '订单来源',
  `memo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `create_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gmt_create` timestamp NULL DEFAULT NULL,
  `modify_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gmt_modified` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`sysno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';

-- ----------------------------
-- Records of so_master
-- ----------------------------
BEGIN;
INSERT INTO `so_master` VALUES (5877786866424549376, NULL, 1, 'SC001', 110105, '朝阳区长安街001号', '000001', '斯密达', '18728828296', 1, 1, 430.000000, 10, NULL, NULL, NULL, NULL, 'dk-order', NULL, NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_unionkey` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
