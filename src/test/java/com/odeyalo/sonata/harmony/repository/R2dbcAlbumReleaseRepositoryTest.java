package com.odeyalo.sonata.harmony.repository;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.ActiveProfiles;

@DataR2dbcTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class R2dbcAlbumReleaseRepositoryTest {

    R2dbcAlbumReleaseRepository testable;



}
