import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import MovieInsightsPerCountryComponentsPage from './movie-insights-per-country.page-object';
import MovieInsightsPerCountryUpdatePage from './movie-insights-per-country-update.page-object';
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

describe('MovieInsightsPerCountry e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let movieInsightsPerCountryComponentsPage: MovieInsightsPerCountryComponentsPage;
  let movieInsightsPerCountryUpdatePage: MovieInsightsPerCountryUpdatePage;

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
    movieInsightsPerCountryComponentsPage = new MovieInsightsPerCountryComponentsPage();
    movieInsightsPerCountryComponentsPage = await movieInsightsPerCountryComponentsPage.goToPage(navBarPage);
  });

  it('should load MovieInsightsPerCountries', async () => {
    expect(await movieInsightsPerCountryComponentsPage.title.getText()).to.match(/Movie Insights Per Countries/);
    expect(await movieInsightsPerCountryComponentsPage.createButton.isEnabled()).to.be.true;
  });

  /* it('should create and delete MovieInsightsPerCountries', async () => {
        const beforeRecordsCount = await isVisible(movieInsightsPerCountryComponentsPage.noRecords) ? 0 : await getRecordsCount(movieInsightsPerCountryComponentsPage.table);
        movieInsightsPerCountryUpdatePage = await movieInsightsPerCountryComponentsPage.goToCreateMovieInsightsPerCountry();
        await movieInsightsPerCountryUpdatePage.enterData();

        expect(await movieInsightsPerCountryComponentsPage.createButton.isEnabled()).to.be.true;
        await waitUntilDisplayed(movieInsightsPerCountryComponentsPage.table);
        await waitUntilCount(movieInsightsPerCountryComponentsPage.records, beforeRecordsCount + 1);
        expect(await movieInsightsPerCountryComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);
        
        await movieInsightsPerCountryComponentsPage.deleteMovieInsightsPerCountry();
        if(beforeRecordsCount !== 0) { 
          await waitUntilCount(movieInsightsPerCountryComponentsPage.records, beforeRecordsCount);
          expect(await movieInsightsPerCountryComponentsPage.records.count()).to.eq(beforeRecordsCount);
        } else {
          await waitUntilDisplayed(movieInsightsPerCountryComponentsPage.noRecords);
        }
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
