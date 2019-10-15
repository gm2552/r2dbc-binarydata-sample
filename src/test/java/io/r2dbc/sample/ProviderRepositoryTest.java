package io.r2dbc.sample;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.test.context.junit4.SpringRunner;

import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProviderCrudApplication.class)
public class ProviderRepositoryTest
{
	@Autowired 
	protected ProviderRepository provRep;
	
	@Autowired 
	protected DatabaseClient database;
	
	@Before
	public void setUp() 
	{

		System.out.println("Createing the provider table.");
		
		List<String> statements = Arrays.asList(//
				"DROP TABLE IF EXISTS provider;",
				"CREATE TABLE provider ( id SERIAL PRIMARY KEY, firstname VARCHAR(100) NOT NULL, lastname VARCHAR(100) NOT NULL, cert BLOB NOT NULL);");
		
		
		statements.forEach(it -> database.execute(it) //
				.fetch() //
				.rowsUpdated() //
				.as(StepVerifier::create) //
				.expectNextCount(1) //
				.verifyComplete());
	
		System.out.println("Createing the provider table.");
	}	
	
	@Test
	public void testAddRetrieveProvider()
	{		
		final Provider prov1 = new Provider(null, "First1", "Last1", new byte[] {1,2,3,4,5});
		final Provider prov2 = new Provider(null, "First2", "Last2", new byte[] {1,2,3,4,5});
		
		/*
		 * Add and verify 
		 */
		this.provRep.saveAll(Arrays.asList(prov1, prov2))//
		.as(StepVerifier::create) //
		.expectNextCount(2) //
		.verifyComplete();
		
		provRep.findAll() //
		.as(StepVerifier::create) //
		.assertNext(prov1::equals) //
		.assertNext(prov2::equals) //
		.verifyComplete();
		
	}
}
