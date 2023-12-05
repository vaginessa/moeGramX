package moe.kirao.mgx.ui;

import android.content.Context;
import android.view.View;

import org.thunderdog.challegram.R;
import org.thunderdog.challegram.component.base.SettingView;
import org.thunderdog.challegram.core.Lang;
import org.thunderdog.challegram.navigation.SettingsWrapBuilder;
import org.thunderdog.challegram.telegram.Tdlib;
import org.thunderdog.challegram.ui.ListItem;
import org.thunderdog.challegram.ui.RecyclerViewController;
import org.thunderdog.challegram.ui.SettingsAdapter;
import org.thunderdog.challegram.v.CustomRecyclerView;

import java.util.ArrayList;

import moe.kirao.mgx.MoexConfig;

public class InterfaceSettingsMoexController extends RecyclerViewController<Void> implements View.OnClickListener {
  private SettingsAdapter adapter;

  public InterfaceSettingsMoexController (Context context, Tdlib tdlib) {
    super(context, tdlib);
  }

  @Override public CharSequence getName () {
    return Lang.getString(R.string.InterfaceMoexSettings);
  }

  @Override public void onClick (View v) {
    int viewId = v.getId();
    if (viewId == R.id.btn_disableCameraButton) {
      MoexConfig.instance().toggleDisableCameraButton();
      adapter.updateValuedSettingById(R.id.btn_disableCameraButton);
    } else if (viewId == R.id.btn_disableRecordButton) {
      MoexConfig.instance().toggleDisableRecordButton();
      adapter.updateValuedSettingById(R.id.btn_disableRecordButton);
    } else if (viewId == R.id.btn_disableCommandsButton) {
      MoexConfig.instance().toggleDisableCommandsButton();
      adapter.updateValuedSettingById(R.id.btn_disableCommandsButton);
    } else if (viewId == R.id.btn_disableSendAsButton) {
      MoexConfig.instance().toggleDisableSendAsButton();
      adapter.updateValuedSettingById(R.id.btn_disableSendAsButton);
    } else if (viewId == R.id.btn_squareAvatar) {
      MoexConfig.instance().toggleSquareAvatar();
      adapter.updateValuedSettingById(R.id.btn_squareAvatar);
    } else if (viewId == R.id.btn_blurDrawer) {
      MoexConfig.instance().toggleBlurDrawer();
      adapter.updateValuedSettingById(R.id.btn_blurDrawer);
    } else if (viewId == R.id.btn_headerText) {
      showChangeHeader();
    } else if (viewId == R.id.btn_disableReactions) {
      MoexConfig.instance().toggleDisableReactions();
      adapter.updateValuedSettingById(R.id.btn_disableReactions);
    }
  }

  private void showChangeHeader () {
    int headerTextOption = MoexConfig.instance().getHeaderText();
    showSettings(new SettingsWrapBuilder(R.id.btn_headerText).setRawItems(new ListItem[] {
      new ListItem(ListItem.TYPE_RADIO_OPTION, R.id.btn_headerTextChats, 0, R.string.Chats, R.id.btn_headerText, headerTextOption == MoexConfig.HEADER_TEXT_CHATS),
      new ListItem(ListItem.TYPE_RADIO_OPTION, R.id.btn_headerTextMoex, 0, R.string.moexHeaderClient, R.id.btn_headerText, headerTextOption == MoexConfig.HEADER_TEXT_MOEX),
      new ListItem(ListItem.TYPE_RADIO_OPTION, R.id.btn_headerTextUsername, 0, R.string.Username, R.id.btn_headerText, headerTextOption == MoexConfig.HEADER_TEXT_USERNAME),
      new ListItem(ListItem.TYPE_RADIO_OPTION, R.id.btn_headerTextName, 0, R.string.login_FirstName, R.id.btn_headerText, headerTextOption == MoexConfig.HEADER_TEXT_NAME),
    }).setIntDelegate((id, result) -> {
      int defaultOption;
      int headerText = result.get(R.id.btn_headerText);
      if (headerText == R.id.btn_headerTextChats) {
        defaultOption = MoexConfig.HEADER_TEXT_CHATS;
      } else if (headerText == R.id.btn_headerTextName) {
        defaultOption = MoexConfig.HEADER_TEXT_NAME;
      } else if (headerText == R.id.btn_headerTextUsername) {
        defaultOption = MoexConfig.HEADER_TEXT_USERNAME;
      } else {
        defaultOption = MoexConfig.HEADER_TEXT_MOEX;
      }
      MoexConfig.instance().setHeaderText(defaultOption);
      adapter.updateValuedSettingById(R.id.btn_headerText);
    }));
  }

  @Override public int getId () {
    return R.id.controller_moexSettings;
  }

  @Override protected void onCreateView (Context context, CustomRecyclerView recyclerView) {
    adapter = new SettingsAdapter(this) {
      @Override protected void setValuedSetting (ListItem item, SettingView view, boolean isUpdate) {
        view.setDrawModifier(item.getDrawModifier());
        int itemId = item.getId();
        if (itemId == R.id.btn_disableCameraButton) {
          view.getToggler().setRadioEnabled(MoexConfig.disableCameraButton, isUpdate);
        } else if (itemId == R.id.btn_disableRecordButton) {
          view.getToggler().setRadioEnabled(MoexConfig.disableRecordButton, isUpdate);
        } else if (itemId == R.id.btn_disableCommandsButton) {
          view.getToggler().setRadioEnabled(MoexConfig.disableCommandsButton, isUpdate);
        } else if (itemId == R.id.btn_disableSendAsButton) {
          view.getToggler().setRadioEnabled(MoexConfig.disableSendAsButton, isUpdate);
        } else if (itemId == R.id.btn_squareAvatar) {
          view.getToggler().setRadioEnabled(MoexConfig.squareAvatar, isUpdate);
        } else if (itemId == R.id.btn_blurDrawer) {
          view.getToggler().setRadioEnabled(MoexConfig.blurDrawer, isUpdate);
        } else if (itemId == R.id.btn_headerText) {
          int mode = MoexConfig.instance().getHeaderText();
          switch (mode) {
            case MoexConfig.HEADER_TEXT_CHATS:
              view.setData(R.string.Chats);
              break;
            case MoexConfig.HEADER_TEXT_MOEX:
              view.setData(R.string.moexHeaderClient);
              break;
            case MoexConfig.HEADER_TEXT_USERNAME:
              view.setData(R.string.Username);
              break;
            case MoexConfig.HEADER_TEXT_NAME:
              view.setData(R.string.login_FirstName);
              break;
          }
        } else if (itemId == R.id.btn_disableReactions) {
          view.getToggler().setRadioEnabled(MoexConfig.disableReactions, isUpdate);
        }
      }
    };

    ArrayList<ListItem> items = new ArrayList<>();
    items.add(new ListItem(ListItem.TYPE_EMPTY_OFFSET_SMALL));
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, R.string.DrawerOptions));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_blurDrawer, 0, R.string.MoexBlurDrawer));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));

    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, R.string.MoexChatsHeader));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_VALUED_SETTING_COMPACT, R.id.btn_headerText, 0, R.string.changeHeaderText));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_squareAvatar, 0, R.string.SquareAvatar));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_disableReactions, 0, R.string.DisableReactions));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));

    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, R.string.MoexHideButtons));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_disableCommandsButton, 0, R.string.DisableCommandsButton));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_disableRecordButton, 0, R.string.DisableRecordButton));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_disableSendAsButton, 0, R.string.DisableSendAsButton));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_disableCameraButton, 0, R.string.DisableCameraButton));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, Lang.getMarkdownString(this, R.string.HideCameraButtonInfo), false));

    adapter.setItems(items, true);
    recyclerView.setAdapter(adapter);
  }
}