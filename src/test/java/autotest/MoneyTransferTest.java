package autotest;

import org.junit.jupiter.api.Test;
import data.DataHelper;
import page.LoginPage;

import static com.codeborne.selenide.Selenide.*;
import static data.DataHelper.*;
import static org.junit.jupiter.api.Assertions.*;

public class MoneyTransferTest {

    @Test
    void shouldTransferWithOneOnTwo() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = getOneCardNum();
        var secondCardInfo = getTwoCardNum();
        int amount = 3000;
        var expectedBalanceOneCard = dashboardPage.getOneCardBalance() - amount;
        var expectedBalanceTwoCard = dashboardPage.getTwoCardBalance() + amount;
        var transferPage = dashboardPage.selectCardToTransfer(secondCardInfo);
        dashboardPage = transferPage.makeTransfer(String.valueOf(amount), firstCardInfo);
        var actualBalanceOneCard = dashboardPage.getOneCardBalance();
        var actualBalanceTwoCard = dashboardPage.getTwoCardBalance();
        assertEquals(expectedBalanceOneCard, actualBalanceOneCard);
        assertEquals(expectedBalanceTwoCard, actualBalanceTwoCard);
    }
}

