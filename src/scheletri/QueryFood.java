package scheletri;

public class QueryFood {
	
	
	//Numero di porzioni per ogni cibo
	
	SELECT p.food_code, COUNT( p.portion_id)
	FROM food f, portion p
	WHERE p.food_code=f.food_code
	GROUP BY p.food_code
	
	
	
	// Numero di porzioni per cibo con calorie minori di valore dato
	
	SELECT p.food_code, COUNT( p.portion_id)
	FROM food f, portion p
	WHERE p.food_code=f.food_code AND p.calories<200
	GROUP BY p.food_code
	
	
	
	// Selezionare cibi che sono anche condimenti
	
	SELECT f.food_code
	FROM food f, condiment c
	WHERE f.food_code=c.condiment_code
	
	
	// Selezionare cibi che hanno più di n condimenti
	
	SELECT fc.food_code, COUNT(fc.condiment_code)
	FROM food_condiment fc
	GROUP BY fc.food_code
	HAVING COUNT(fc.condiment_code)>4
	
	
	
	// Adiacenza fra cibi che hanno condimenti in comune
	
	SELECT fc1.food_code, fc2.food_code, COUNT(DISTINCT fc1.condiment_code)
	FROM condiment c, food_condiment fc1, food_condiment fc2
	WHERE fc1.food_code>fc2.food_code
	AND fc1.condiment_code=fc2.condiment_code
	GROUP BY fc1.food_code, fc2.food_code
	
	
	
	// Adiacenza fra cibi che hanno in comune condimenti aventi (parametro) calorie inferiori a numero dato
	
	SELECT fc1.food_code, fc2.food_code, COUNT(DISTINCT fc1.condiment_code)
	FROM condiment c, food_condiment fc1, food_condiment fc2
	WHERE fc1.food_code>fc2.food_code
	AND fc1.condiment_code=c.condiment_code
	AND fc1.condiment_code=fc2.condiment_code
	AND c.condiment_calories<20
	GROUP BY fc1.food_code, fc2.food_code
	
	
	
	// Adiacenza fra SOLI cibi aventi (parametro) porzioni con calorie minori di un certo valore, 
	// aventi in comune condimenti (parametro) con calorie inferiori a numero dato
	
	SELECT fc1.food_code, fc2.food_code, COUNT(DISTINCT fc1.condiment_code)
	FROM portion p1, portion p2, condiment c, food_condiment fc1, food_condiment fc2
	WHERE fc1.food_code>fc2.food_code AND fc1.food_code=p1.food_code AND fc2.food_code=p2.food_code
	AND p1.calories<10 AND p2.calories<10
	AND fc1.condiment_code=c.condiment_code
	AND fc1.condiment_code=fc2.condiment_code
	AND c.condiment_calories<20
	GROUP BY fc1.food_code, fc2.food_code

}
