package baobab;

import com.cegeka.switchfully.RestAssuredTest;
import com.cegeka.switchfully.security.army.ArmyResource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;

public class BaobabTest extends RestAssuredTest {

    @Nested
    @DisplayName("When Get Deployed Army Info")
    class DeployedArmyInfo {

        @Test
        void getDeployedArmyInfo_givenUserWithRoleCivilian_ThenShouldGetForbidden() {
            givenRequestForUser("ZWANETTA", "WORST")
                    .when()
                    .get(String.format("%s/%s", ArmyResource.ARMY_RESOURCE_PATH, "Belgium"))
                    .then()
                    .assertThat()
                    .statusCode(FORBIDDEN.value());
        }

        @Test
        void getDeployedArmyInfo_givenUserWithRoleHumanRelations_ThenShouldGetForbidden() {
            givenRequestForUser("UNCLE", "SAM")
                    .when()
                    .get(String.format("%s/%s", ArmyResource.ARMY_RESOURCE_PATH, "Belgium"))
                    .then()
                    .assertThat()
                    .statusCode(FORBIDDEN.value());
        }

        @Test
        void getDeployedArmyInfo_givenUserWithRolePrivate_ThenShouldGetResult() {
            givenRequestForUser("JMILLER", "THANKS")
                    .when()
                    .get(String.format("%s/%s", ArmyResource.ARMY_RESOURCE_PATH, "Belgium"))
                    .then()
                    .assertThat()
                    .statusCode(OK.value());
        }

        @Test
        void getDeployedArmyInfo_givenUserWithRoleGeneral_ThenShouldGetResult() {
            givenRequestForUser("GENNY", "RALLY")
                    .when()
                    .get(String.format("%s/%s", ArmyResource.ARMY_RESOURCE_PATH, "Belgium"))
                    .then()
                    .assertThat()
                    .statusCode(OK.value());
        }
    }

    @Nested
    @DisplayName("When Launch Nukes")
    class LaunchNukes {

        @Test
        void launchNukes_givenUserWithRoleCivilian_ThenShouldGetForbidden() {
            givenRequestForUser("ZWANETTA", "WORST")
                    .when()
                    .get(String.format("%s/%s", ArmyResource.ARMY_RESOURCE_PATH, "nuke"))
                    .then()
                    .assertThat()
                    .statusCode(FORBIDDEN.value());
        }

        @Test
        void launchNukes_givenUserWithRoleHumanRelations_ThenShouldGetForbidden() {
            givenRequestForUser("UNCLE", "SAM")
                    .when()
                    .get(String.format("%s/%s", ArmyResource.ARMY_RESOURCE_PATH, "nuke"))
                    .then()
                    .assertThat()
                    .statusCode(FORBIDDEN.value());
        }

        @Test
        void launchNukes_givenUserWithRolePrivate_ThenShouldGetForbidden() {
            givenRequestForUser("JMILLER", "THANKS")
                    .when()
                    .get(String.format("%s/%s", ArmyResource.ARMY_RESOURCE_PATH, "nuke"))
                    .then()
                    .assertThat()
                    .statusCode(FORBIDDEN.value());
        }

        @Test
        void launchNukes_givenUserWithRoleGeneral_ThenShouldGetResult() {
            givenRequestForUser("GENNY", "RALLY")
                    .when()
                    .get(String.format("%s/%s", ArmyResource.ARMY_RESOURCE_PATH, "nuke"))
                    .then()
                    .assertThat()
                    .statusCode(OK.value());
        }
    }

    @Nested
    @DisplayName("When Join Army")
    class JoinArmy {

        @Test
        void joinArmy_givenUserWithRoleCivilian_ThenShouldGetResult() {
            givenRequestForUser("ZWANETTA", "WORST")
                    .when()
                    .post(ArmyResource.ARMY_RESOURCE_PATH)
                    .then()
                    .assertThat()
                    .statusCode(OK.value());
        }

        @Test
        void joinArmy_givenUserWithRoleHumanRelations_ThenShouldGetForbidden() {
            givenRequestForUser("UNCLE", "SAM")
                    .when()
                    .post(ArmyResource.ARMY_RESOURCE_PATH)
                    .then()
                    .assertThat()
                    .statusCode(FORBIDDEN.value());
        }

        @Test
        void joinArmy_givenUserWithRolePrivate_ThenShouldGetForbidden() {
            givenRequestForUser("JMILLER", "THANKS")
                    .when()
                    .post(ArmyResource.ARMY_RESOURCE_PATH)
                    .then()
                    .assertThat()
                    .statusCode(FORBIDDEN.value());
        }

        @Test
        void joinArmy_givenUserWithRoleGeneral_ThenShouldGetForbidden() {
            givenRequestForUser("GENNY", "RALLY")
                    .when()
                    .post(ArmyResource.ARMY_RESOURCE_PATH)
                    .then()
                    .assertThat()
                    .statusCode(FORBIDDEN.value());
        }
    }

    @Nested
    @DisplayName("When Promote Private")
    class PromotePrivate {

        @Test
        void promotePrivate_givenUserWithRoleCivilian_ThenShouldGetForbidden() {
            givenRequestForUser("ZWANETTA", "WORST")
                    .when()
                    .post(String.format("%s/%s/%s", ArmyResource.ARMY_RESOURCE_PATH, "promote", "ZWANETTA"))
                    .then()
                    .assertThat()
                    .statusCode(FORBIDDEN.value());
        }

        @Test
        void promotePrivate_givenUserWithRoleHumanRelations_ThenShouldGetResult() {
            givenRequestForUser("UNCLE", "SAM")
                    .when()
                    .post(String.format("%s/%s/%s", ArmyResource.ARMY_RESOURCE_PATH, "promote", "ZWANETTA"))
                    .then()
                    .assertThat()
                    .statusCode(OK.value());
        }

        @Test
        void promotePrivate_givenUserWithRolePrivate_ThenShouldGetForbidden() {
            givenRequestForUser("JMILLER", "THANKS")
                    .when()
                    .post(String.format("%s/%s/%s", ArmyResource.ARMY_RESOURCE_PATH, "promote", "ZWANETTA"))
                    .then()
                    .assertThat()
                    .statusCode(FORBIDDEN.value());
        }

        @Test
        void promotePrivate_givenUserWithRoleGeneral_ThenShouldGetForbidden() {
            givenRequestForUser("GENNY", "RALLY")
                    .when()
                    .post(String.format("%s/%s/%s", ArmyResource.ARMY_RESOURCE_PATH, "promote", "ZWANETTA"))
                    .then()
                    .assertThat()
                    .statusCode(FORBIDDEN.value());
        }
    }

    @Nested
    @DisplayName("When Discharge Private")
    class DischargePrivate {

        @Test
        void dischargePrivate_givenUserWithRoleCivilian_ThenShouldGetForbidden() {
            givenRequestForUser("ZWANETTA", "WORST")
                    .when()
                    .post(String.format("%s/%s/%s", ArmyResource.ARMY_RESOURCE_PATH, "discharge", "ZWANETTA"))
                    .then()
                    .assertThat()
                    .statusCode(FORBIDDEN.value());
        }

        @Test
        void dischargePrivate_givenUserWithRoleHumanRelations_ThenShouldGetResult() {
            givenRequestForUser("UNCLE", "SAM")
                    .when()
                    .post(String.format("%s/%s/%s", ArmyResource.ARMY_RESOURCE_PATH, "discharge", "ZWANETTA"))
                    .then()
                    .assertThat()
                    .statusCode(OK.value());
        }

        @Test
        void dischargePrivate_givenUserWithRolePrivate_ThenShouldGetForbidden() {
            givenRequestForUser("JMILLER", "THANKS")
                    .when()
                    .post(String.format("%s/%s/%s", ArmyResource.ARMY_RESOURCE_PATH, "discharge", "ZWANETTA"))
                    .then()
                    .assertThat()
                    .statusCode(FORBIDDEN.value());
        }

        @Test
        void dischargePrivate_givenUserWithRoleGeneral_ThenShouldGetForbidden() {
            givenRequestForUser("GENNY", "RALLY")
                    .when()
                    .post(String.format("%s/%s/%s", ArmyResource.ARMY_RESOURCE_PATH, "discharge", "ZWANETTA"))
                    .then()
                    .assertThat()
                    .statusCode(FORBIDDEN.value());
        }
    }
}
