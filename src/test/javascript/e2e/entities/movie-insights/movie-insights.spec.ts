import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import MovieInsightsComponentsPage from './movie-insights.page-object';
import MovieInsightsUpdatePage from './movie-insights-update.page-object';
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

describe('MovieInsights e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let movieInsightsComponentsPage: MovieInsightsComponentsPage;
  let movieInsightsUpdatePage: MovieInsightsUpdatePage;

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
    movieInsightsComponentsPage = new MovieInsightsComponentsPage();
    movieInsightsComponentsPage = await movieInsightsComponentsPage.goToPage(navBarPage);
  });

  it('should load MovieInsights', async () => {
    expect(await movieInsightsComponentsPage.title.getText()).to.match(/Movie Insights/);
    expect(await movieInsightsComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete MovieInsights', async () => {
    const beforeRecordsCount = (await isVisible(movieInsightsComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(movieInsightsComponentsPage.table);
    movieInsightsUpdatePage = await movieInsightsComponentsPage.goToCreateMovieInsights();
    await movieInsightsUpdatePage.enterData();

    expect(await movieInsightsComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(movieInsightsComponentsPage.table);
    await waitUntilCount(movieInsightsComponentsPage.records, beforeRecordsCount + 1);
    expect(await movieInsightsComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await movieInsightsComponentsPage.deleteMovieInsights();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(movieInsightsComponentsPage.records, beforeRecordsCount);
      expect(await movieInsightsComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(movieInsightsComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
