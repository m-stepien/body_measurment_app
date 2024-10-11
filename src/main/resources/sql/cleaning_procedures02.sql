
CREATE PROCEDURE delete_lonly_basic_circumference()
LANGUAGE plpgsql
AS $$
DECLARE
    not_use_bc INT[];
BEGIN
	SELECT ARRAY(
		SELECT bc.id
		FROM basic_circumference bc
		LEFT JOIN circumference_data cd  ON bc.id = cd.basic_circumference_id
		WHERE cd.basic_circumference_id IS NULL)
	INTO not_use_bc;
	IF array_length(not_use_bc, 1) IS NOT NULL
	THEN
		DELETE FROM basic_circumference WHERE id = ANY(not_use_bc);
	END IF;
END;
$$;

CREATE PROCEDURE delete_lonly_additional_circumference()
LANGUAGE plpgsql
AS $$
DECLARE
    not_use_ac INT[];
BEGIN
	SELECT ARRAY(
		SELECT ac.id
		FROM additional_circumference ac
		LEFT JOIN circumference_data cd  ON ac.id = cd.additional_circumference_id
		WHERE cd.additional_circumference_id IS NULL)
	INTO not_use_ac;
	IF array_length(not_use_ac, 1) IS NOT NULL THEN
		DELETE FROM additional_circumference WHERE id = ANY(not_use_ac);
	END IF;
END;
$$;

CREATE PROCEDURE delete_lonly_circumference_data()
LANGUAGE plpgsql
AS $$
DECLARE
    not_use_cd INT[];
BEGIN
	SELECT ARRAY(
		SELECT cd.id FROM circumference_data cd LEFT JOIN
				 weight w ON cd.measurement_date = w."date" WHERE w."date" IS NULL
		) INTO not_use_cd;
	IF array_length(not_use_cd, 1) IS NOT NULL THEN
		DELETE FROM circumference_data WHERE id = ANY(not_use_cd);
	END IF;
END;
$$;

CREATE PROCEDURE delete_lonly_weight()
LANGUAGE plpgsql
AS $$
BEGIN
	DELETE FROM weight WHERE "date" is NULL;
END;
$$;

CREATE PROCEDURE delete_lonly_data()
LANGUAGE plpgsql
AS $$
BEGIN
	CALL delete_lonly_basic_circumference();
	CALL delete_lonly_additional_circumference();
	CALL delete_lonly_circumference_data();
	CALL delete_lonly_weight();
END;
$$;