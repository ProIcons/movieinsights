import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import MovieInsightsPerYearComponentsPage from './movie-insights-per-year.page-object';
import MovieInsightsPerYearUpdatePage from './movie-insights-per-year-update.page-object';
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

describe('MovieInsightsPerYear e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let movieInsightsPerYearComponentsPage: MovieInsightsPerYearComponentsPage;
  let movieInsightsPerYearUpdatePage: MovieInsightsPerYearUpdatePage;

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
    movieInsightsPerYearComponentsPage = new MovieInsightsPerYearComponentsPage();
    movieInsightsPerYearComponentsPage = await movieInsightsPerYearComponentsPage.goToPage(navBarPage);
  });

  it('should load MovieInsightsPerYears', async () => {
    expect(await movieInsightsPerYearComponentsPage.title.getText()).to.match(/Movie Insights Per Years/);
    expect(await movieInsightsPerYearComponentsPage.createButton.isEnabled()).to.be.true;
  });

  /* it('should create and delete MovieInsightsPerYears', async () => {
        const beforeRecordsCount = await isVisible(movieInsightsPerYearComponentsPage.noRecords) ? 0 : await getRecordsCount(movieInsightsPerYearComponentsPage.table);
        movieInsightsPerYearUpdatePage = await movieInsightsPerYearComponentsPage.goToCreateMovieInsightsPerYear();
        await movieInsightsPerYearUpdatePage.enterData();

        expect(await movieInsightsPerYearComponentsPage.createButton.isEnabled()).to.be.true;
        await waitUntilDisplayed(movieInsightsPerYearComponentsPage.table);
        await waitUntilCount(movieInsightsPerYearComponentsPage.records, beforeRecordsCount + 1);
        expect(await movieInsightsPerYearComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);
        
        await movieInsightsPerYearComponentsPage.deleteMovieInsightsPerYear();
        if(beforeRecordsCount !== 0) { 
          await waitUntilCount(movieInsightsPerYearComponentsPage.records, beforeRecordsCount);
          expect(await movieInsightsPerYearComponentsPage.records.count()).to.eq(beforeRecordsCount);
        } else {
          await waitUntilDisplayed(movieInsightsPerYearComponentsPage.noRecords);
        }
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
