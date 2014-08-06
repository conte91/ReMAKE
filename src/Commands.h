#ifdef __cplusplus
extern "C" {
#endif
void initTool();
void MoveMouse(int x, int y);
void LeftClick();
void doClick(int button);
void MoveMouseRelative(int x, int y);
void sendKeys(const char* string, int newline);
#ifdef __cplusplus
}
#endif
