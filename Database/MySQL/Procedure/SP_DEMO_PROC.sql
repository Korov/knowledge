DELIMITER $$

DROP PROCEDURE IF EXISTS SP_DEMO_PROC $$

CREATE PROCEDURE SP_DEMO_PROC(IN MIN_VALUE INT,IN MAX_VALUE INT, OUT DEMO_RESULT VARCHAR(1024))
LAB_SP_DEMO_PROC:
BEGIN
    DECLARE V_TEMP INT DEFAULT 1;
    DECLARE DUPLICATE_KEY CONDITION FOR 1062;
    DECLARE CONTINUE HANDLER FOR DUPLICATE_KEY
    BEGIN
        SET DEMO_RESULT='Duplicate key error';
    END;
    
    SET V_TEMP=MIN_VALUE;
    
    DROP TABLE IF EXISTS `TBL_VALUE_TEMP`;
    CREATE TABLE `TBL_VALUE_TEMP`(
        `ID` INT(11) NOT NULL AUTO_INCREMENT,
        `DEFAULT_VALUE` INT(11) DEFAULT 0,
        `VALUE` INT(11) DEFAULT 0,
        PRIMARY KEY(`ID`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
    
    WHILE V_TEMP<=MAX_VALUE DO
        INSERT INTO `TBL_VALUE_TEMP`(`DEFAULT_VALUE`) VALUES (V_TEMP);
        SET V_TEMP=V_TEMP+1;
    END WHILE;
    
    CALL DEMO_SQUARE(DEMO_RESULT);
END LAB_SP_DEMO_PROC;
$$
DELIMITER ;