import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ProductionCountryComponentsPage from './production-country.page-object';
import ProductionCountryUpdatePage from './production-country-update.page-object';
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

describe('ProductionCountry e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let productionCountryComponentsPage: ProductionCountryComponentsPage;
  let productionCountryUpdatePage: ProductionCountryUpdatePage;

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
    productionCountryComponentsPage = new ProductionCountryComponentsPage();
    productionCountryComponentsPage = await productionCountryComponentsPage.goToPage(navBarPage);
  });

  it('should load ProductionCountries', async () => {
    expect(await productionCountryComponentsPage.title.getText()).to.match(/Production Countries/);
    expect(await productionCountryComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete ProductionCountries', async () => {
    const beforeRecordsCount = (await isVisible(productionCountryComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(productionCountryComponentsPage.table);
    productionCountryUpdatePage = await productionCountryComponentsPage.goToCreateProductionCountry();
    await productionCountryUpdatePage.enterData();

    expect(await productionCountryComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(productionCountryComponentsPage.table);
    await waitUntilCount(productionCountryComponentsPage.records, beforeRecordsCount + 1);
    expect(await productionCountryComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await productionCountryComponentsPage.deleteProductionCountry();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(productionCountryComponentsPage.records, beforeRecordsCount);
      expect(await productionCountryComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(productionCountryComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
