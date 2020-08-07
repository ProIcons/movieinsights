import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import MovieInsightsPerCompanyComponentsPage from './movie-insights-per-company.page-object';
import MovieInsightsPerCompanyUpdatePage from './movie-insights-per-company-update.page-object';
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

describe('MovieInsightsPerCompany e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let movieInsightsPerCompanyComponentsPage: MovieInsightsPerCompanyComponentsPage;
  let movieInsightsPerCompanyUpdatePage: MovieInsightsPerCompanyUpdatePage;

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
    movieInsightsPerCompanyComponentsPage = new MovieInsightsPerCompanyComponentsPage();
    movieInsightsPerCompanyComponentsPage = await movieInsightsPerCompanyComponentsPage.goToPage(navBarPage);
  });

  it('should load MovieInsightsPerCompanies', async () => {
    expect(await movieInsightsPerCompanyComponentsPage.title.getText()).to.match(/Movie Insights Per Companies/);
    expect(await movieInsightsPerCompanyComponentsPage.createButton.isEnabled()).to.be.true;
  });

  /* it('should create and delete MovieInsightsPerCompanies', async () => {
        const beforeRecordsCount = await isVisible(movieInsightsPerCompanyComponentsPage.noRecords) ? 0 : await getRecordsCount(movieInsightsPerCompanyComponentsPage.table);
        movieInsightsPerCompanyUpdatePage = await movieInsightsPerCompanyComponentsPage.goToCreateMovieInsightsPerCompany();
        await movieInsightsPerCompanyUpdatePage.enterData();

        expect(await movieInsightsPerCompanyComponentsPage.createButton.isEnabled()).to.be.true;
        await waitUntilDisplayed(movieInsightsPerCompanyComponentsPage.table);
        await waitUntilCount(movieInsightsPerCompanyComponentsPage.records, beforeRecordsCount + 1);
        expect(await movieInsightsPerCompanyComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);
        
        await movieInsightsPerCompanyComponentsPage.deleteMovieInsightsPerCompany();
        if(beforeRecordsCount !== 0) { 
          await waitUntilCount(movieInsightsPerCompanyComponentsPage.records, beforeRecordsCount);
          expect(await movieInsightsPerCompanyComponentsPage.records.count()).to.eq(beforeRecordsCount);
        } else {
          await waitUntilDisplayed(movieInsightsPerCompanyComponentsPage.noRecords);
        }
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
