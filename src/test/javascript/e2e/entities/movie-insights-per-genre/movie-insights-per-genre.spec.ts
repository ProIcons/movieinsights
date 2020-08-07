import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import MovieInsightsPerGenreComponentsPage from './movie-insights-per-genre.page-object';
import MovieInsightsPerGenreUpdatePage from './movie-insights-per-genre-update.page-object';
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

describe('MovieInsightsPerGenre e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let movieInsightsPerGenreComponentsPage: MovieInsightsPerGenreComponentsPage;
  let movieInsightsPerGenreUpdatePage: MovieInsightsPerGenreUpdatePage;

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
    movieInsightsPerGenreComponentsPage = new MovieInsightsPerGenreComponentsPage();
    movieInsightsPerGenreComponentsPage = await movieInsightsPerGenreComponentsPage.goToPage(navBarPage);
  });

  it('should load MovieInsightsPerGenres', async () => {
    expect(await movieInsightsPerGenreComponentsPage.title.getText()).to.match(/Movie Insights Per Genres/);
    expect(await movieInsightsPerGenreComponentsPage.createButton.isEnabled()).to.be.true;
  });

  /* it('should create and delete MovieInsightsPerGenres', async () => {
        const beforeRecordsCount = await isVisible(movieInsightsPerGenreComponentsPage.noRecords) ? 0 : await getRecordsCount(movieInsightsPerGenreComponentsPage.table);
        movieInsightsPerGenreUpdatePage = await movieInsightsPerGenreComponentsPage.goToCreateMovieInsightsPerGenre();
        await movieInsightsPerGenreUpdatePage.enterData();

        expect(await movieInsightsPerGenreComponentsPage.createButton.isEnabled()).to.be.true;
        await waitUntilDisplayed(movieInsightsPerGenreComponentsPage.table);
        await waitUntilCount(movieInsightsPerGenreComponentsPage.records, beforeRecordsCount + 1);
        expect(await movieInsightsPerGenreComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);
        
        await movieInsightsPerGenreComponentsPage.deleteMovieInsightsPerGenre();
        if(beforeRecordsCount !== 0) { 
          await waitUntilCount(movieInsightsPerGenreComponentsPage.records, beforeRecordsCount);
          expect(await movieInsightsPerGenreComponentsPage.records.count()).to.eq(beforeRecordsCount);
        } else {
          await waitUntilDisplayed(movieInsightsPerGenreComponentsPage.noRecords);
        }
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
