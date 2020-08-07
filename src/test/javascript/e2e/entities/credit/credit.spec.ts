import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import CreditComponentsPage from './credit.page-object';
import CreditUpdatePage from './credit-update.page-object';
import {
  waitUntilDisplayed,
  waitUntilAnyDisplayed,
  click,
  getRecordsCount,
  waitUntilHidden,
  waitUntilCount,
  isVisible,
} from '../../util/utils';

const expect = chai.expect;

describe('Credit e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let creditComponentsPage: CreditComponentsPage;
  let creditUpdatePage: CreditUpdatePage;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.waitUntilDisplayed();

    await signInPage.username.sendKeys('admin');
    await signInPage.password.sendKeys('admin');
    await signInPage.loginButton.click();
    await signInPage.waitUntilHidden();
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  beforeEach(async () => {
    await browser.get('/');
    await waitUntilDisplayed(navBarPage.entityMenu);
    creditComponentsPage = new CreditComponentsPage();
    creditComponentsPage = await creditComponentsPage.goToPage(navBarPage);
  });

  it('should load Credits', async () => {
    expect(await creditComponentsPage.title.getText()).to.match(/Credits/);
    expect(await creditComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete Credits', async () => {
    const beforeRecordsCount = (await isVisible(creditComponentsPage.noRecords)) ? 0 : await getRecordsCount(creditComponentsPage.table);
    creditUpdatePage = await creditComponentsPage.goToCreateCredit();
    await creditUpdatePage.enterData();

    expect(await creditComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(creditComponentsPage.table);
    await waitUntilCount(creditComponentsPage.records, beforeRecordsCount + 1);
    expect(await creditComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await creditComponentsPage.deleteCredit();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(creditComponentsPage.records, beforeRecordsCount);
      expect(await creditComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(creditComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
