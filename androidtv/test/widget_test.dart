import 'package:flutter_test/flutter_test.dart';
import 'package:palco_androidtv/main.dart';

void main() {
  test('app constrói sem exceção', () {
    // smoke: construção do widget raiz não deve lançar
    final app = PalcoTvApp();
    expect(app, isNotNull);
  });
}
