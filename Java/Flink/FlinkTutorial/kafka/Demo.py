import logging
import logging.handlers

if __name__ == "__main__":
    bytesObj = b'{\n "_alert_domain_id": 1,\n "_alert_domain_token": "79f56985674332a432b5e6e2b9d87b52",\n "_alert_history_id": "4520_1645690724837_0",\n "_alert_id": 4520,\n "_alert_owner_id": 84,\n "_alert_owner_name": "rizhiyiadmin",\n "_alert_plugins": [],\n "check_interval": 60000,\n "description": "ids-\xe6\xb5\x8b\xe8\xaf\x95",\n "exec_time": 1645690724908.0,\n "extend_conf": {\n "threat_type1": "",\n "threat_type2": "",\n "threat_stage": "\xe4\xbf\xa1\xe6\x81\xaf\xe6\x8e\xa2\xe6\xb5\x8b"\n },\n "graph_enabled": false,\n "is_alert_recovery": false,\n "is_segmentation": false,\n "level": "low",\n "name": "ids-\xe6\xb5\x8b\xe8\xaf\x95",\n "plugin": [],\n "resource_groups": [],\n "result": {\n "columns": [\n {\n "name": "dst_ip",\n "groupby": true,\n "type": "UNKNOWN"\n },\n {\n "name": "src_ip",\n "groupby": false,\n "type": "OBJECT"\n },\n {\n "name": "cnt",\n "groupby": false,\n "type": "LONG"\n }\n ],\n "extend_hits": [],\n "extend_result": {},\n "extend_result_sheets_total": 0,\n "extend_result_total_hits": 0,\n "extend_total": 0,\n "hits": [\n {\n "src_ip": [\n "192.168.1.156"\n ],\n "cnt": 2,\n "dst_ip": "192.168.1.130"\n }\n ],\n "is_extend_query_timechart": false,\n "total": 1,\n "value": 2.0\n },\n "search": {\n "datasets": "[]",\n "extend_datasets": "[]",\n "query": "( (appname:ids) AND (src_ip:192.168.1.156) ) | stats count() as cnt , values(src_ip) as src_ip by dst_ip, src_ip"\n },\n "send_time": 1645690726065.0,\n "strategy": {\n "description": "SPL\xe5\x91\x8a\xe8\xad\xa6",\n "name": "spl_query",\n "trigger": {\n "alert_thresholds": "{\\"low\\":[0.0]}",\n "category": 4,\n "compare": ">",\n "compare_style": "fixed",\n "compare_value": [\n 0.0\n ],\n "end_time": 1645690724837.0,\n "field": "cnt",\n "level": "low",\n "start_time": 1645690424837.0,\n "time_range": "-5",\n "time_range_unit": "m"\n }\n },\n "traceid": "Alert_4520_1645690724837",\n "trigger_timestamp": 1645690724837\n}'
    # print(bytesObj.decode('utf-8'))

    logger = logging.getLogger()
    fh = logging.handlers.SysLogHandler(('192.168.50.163', 514), logging.handlers.SysLogHandler.LOG_AUTH)
    formatter = logging.Formatter(' IP 192.168.50.118 %(asctime)s - %(name)s - %(levelname)s - %(message)s')

    fh.setFormatter(formatter)
    print(' IP 192.168.50.118 %(asctime)s - %(name)s - %(levelname)s - %(message)s')
    logger.addHandler(fh)
    logger.warning("msg")
    logger.error("msg")



