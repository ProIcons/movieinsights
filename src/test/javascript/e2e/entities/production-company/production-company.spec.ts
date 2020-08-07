import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ProductionCompanyComponentsPage from './production-company.page-object';
import ProductionCompanyUpdatePage from './production-company-update.page-object';
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

describe('ProductionCompany e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let productionCompanyComponentsPage: ProductionCompanyComponentsPage;
  let productionCompanyUpdatePage: ProductionCompanyUpdatePage;

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
    productionCompanyComponentsPage = new ProductionCompanyComponentsPage();
    productionCompanyComponentsPage = await productionCompanyComponentsPage.goToPage(navBarPage);
  });

  it('should load ProductionCompanies', async () => {
    expect(await productionCompanyComponentsPage.title.getText()).to.match(/Production Companies/);
    expect(await productionCompanyComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete ProductionCompanies', async () => {
    const beforeRecordsCount = (await isVisible(productionCompanyComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(productionCompanyComponentsPage.table);
    productionCompanyUpdatePage = await productionCompanyComponentsPage.goToCreateProductionCompany();
    await productionCompanyUpdatePage.enterData();

    expect(await productionCompanyComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(productionCompanyComponentsPage.table);
    await waitUntilCount(productionCompanyComponentsPage.records, beforeRecordsCount + 1);
    expect(await productionCompanyComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await productionCompanyComponentsPage.deleteProductionCompany();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(productionCompanyComponentsPage.records, beforeRecordsCount);
      expect(await productionCompanyComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(productionCompanyComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
