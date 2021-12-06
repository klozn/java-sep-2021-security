package avocado;

import com.cegeka.switchfully.security.army.ArmyInfoDto;
import com.cegeka.switchfully.security.army.ArmyResource;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

class AvocadoTest extends RestAssuredTest {

    @Test
    void getDeployedArmyInfo_givenKnownUsernameAndPasswordEncodedAsBasicAuthenticationHeader_thenShouldAllowAccess() {
        ArmyInfoDto actual = givenRequestForUser("JMILLER", "THANKS")
                .when()
                .get(String.format("%s/%s", ArmyResource.ARMY_RESOURCE_PATH, "Belgium"))
                .then()
                .assertThat()
                .statusCode(OK.value())
                .extract()
                .body()
                .as(ArmyInfoDto.class);

        assertThat(actual.country).isEqualTo("Belgium");
    }

    @Test
    void getDeployedArmyInfo_givenKnownUsernameAndWrongPasswordEncodedAsBasicAuthenticationHeader_thenShouldNotAllowAccess() {
        givenRequestForUser("JMILLER", "JBAKER")
                .when()
                .get(String.format("%s/%s", ArmyResource.ARMY_RESOURCE_PATH, "Belgium"))
                .then()
                .assertThat()
                .statusCode(UNAUTHORIZED.value());
    }

    @Test
    void getDeployedArmyInfo_givenUnknownUsernameAndPasswordEncodedAsBasicAuthenticationHeader_thenShouldNotAllowAccess() {
        givenRequestForUser("FONZ", "AYE")
                .when()
                .get(String.format("%s/%s", ArmyResource.ARMY_RESOURCE_PATH, "Belgium"))
                .then()
                .assertThat()
                .statusCode(UNAUTHORIZED.value());
    }
}
