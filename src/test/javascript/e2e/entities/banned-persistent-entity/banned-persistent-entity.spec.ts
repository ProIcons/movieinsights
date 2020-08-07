import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import BannedPersistentEntityComponentsPage from './banned-persistent-entity.page-object';
import BannedPersistentEntityUpdatePage from './banned-persistent-entity-update.page-object';
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

describe('BannedPersistentEntity e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let bannedPersistentEntityComponentsPage: BannedPersistentEntityComponentsPage;
  let bannedPersistentEntityUpdatePage: BannedPersistentEntityUpdatePage;

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
    bannedPersistentEntityComponentsPage = new BannedPersistentEntityComponentsPage();
    bannedPersistentEntityComponentsPage = await bannedPersistentEntityComponentsPage.goToPage(navBarPage);
  });

  it('should load BannedPersistentEntities', async () => {
    expect(await bannedPersistentEntityComponentsPage.title.getText()).to.match(/Banned Persistent Entities/);
    expect(await bannedPersistentEntityComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete BannedPersistentEntities', async () => {
    const beforeRecordsCount = (await isVisible(bannedPersistentEntityComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(bannedPersistentEntityComponentsPage.table);
    bannedPersistentEntityUpdatePage = await bannedPersistentEntityComponentsPage.goToCreateBannedPersistentEntity();
    await bannedPersistentEntityUpdatePage.enterData();

    expect(await bannedPersistentEntityComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(bannedPersistentEntityComponentsPage.table);
    await waitUntilCount(bannedPersistentEntityComponentsPage.records, beforeRecordsCount + 1);
    expect(await bannedPersistentEntityComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await bannedPersistentEntityComponentsPage.deleteBannedPersistentEntity();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(bannedPersistentEntityComponentsPage.records, beforeRecordsCount);
      expect(await bannedPersistentEntityComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(bannedPersistentEntityComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
