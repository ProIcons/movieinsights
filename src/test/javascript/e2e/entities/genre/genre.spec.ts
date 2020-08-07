import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import GenreComponentsPage from './genre.page-object';
import GenreUpdatePage from './genre-update.page-object';
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

describe('Genre e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let genreComponentsPage: GenreComponentsPage;
  let genreUpdatePage: GenreUpdatePage;

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
    genreComponentsPage = new GenreComponentsPage();
    genreComponentsPage = await genreComponentsPage.goToPage(navBarPage);
  });

  it('should load Genres', async () => {
    expect(await genreComponentsPage.title.getText()).to.match(/Genres/);
    expect(await genreComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete Genres', async () => {
    const beforeRecordsCount = (await isVisible(genreComponentsPage.noRecords)) ? 0 : await getRecordsCount(genreComponentsPage.table);
    genreUpdatePage = await genreComponentsPage.goToCreateGenre();
    await genreUpdatePage.enterData();

    expect(await genreComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(genreComponentsPage.table);
    await waitUntilCount(genreComponentsPage.records, beforeRecordsCount + 1);
    expect(await genreComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await genreComponentsPage.deleteGenre();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(genreComponentsPage.records, beforeRecordsCount);
      expect(await genreComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(genreComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
